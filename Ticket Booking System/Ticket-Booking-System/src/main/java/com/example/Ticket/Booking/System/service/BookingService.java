package com.example.Ticket.Booking.System.service;

import com.example.Ticket.Booking.System.model.*;
import com.example.Ticket.Booking.System.repository.ConfigRepository;
import com.example.Ticket.Booking.System.repository.CustomerLogRepository;
import com.example.Ticket.Booking.System.repository.CustomerRegistrationRepository;
import com.example.Ticket.Booking.System.repository.TicketPoolRepository;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@Service
public class BookingService {

    @Autowired
    private CustomerLogRepository customerLogRepository;

    @Autowired
    private ConfigService configService;

    @Autowired
    private TicketPoolRepository ticketPoolRepository;

    @Autowired
    private ConfigRepository configRepository;

    @Autowired
    private CustomerRegistrationRepository customerRegistrationRepository;


    @Transactional
    public  int bookTicket(Customer customer, int numOfTickets){
        // Create a TicketBookingTask with customer, number of tickets, and the current service
        TicketBookingRequest task = new TicketBookingRequest(customer,numOfTickets,this);
        Thread thread = new Thread(task, customer.getEmail());
        thread.start();
        int val = task.getNumOfTickets();
        return val;

    }

    public synchronized Customer getCustomerDetails(Customer customer) {

        if (customer != null) {

            String customerEmail = customer.getEmail();
            Optional<Customer> existingVendor = customerRegistrationRepository.findByEmail(customerEmail);
            if (existingVendor.isPresent()) {
                return existingVendor.get();

            } else {
                System.out.println("Customer not found, not updating TicketPool.");
            }
        }
        return null;
    }

    public Configuration getTicketPoolConfig() {
        return  configService.readConfigDataBase();
    }

    //give the count of how many ticket can purchase
    public synchronized int getCountOfTicketCan(String threadName,int countBook,Customer customer,BookingService bookingService){

        Configuration configDetails =  bookingService.getTicketPoolConfig();
        Customer customerDetails = getCustomerDetails(customer);

        if(customerDetails == null){return 0;}
        int canBookCount =0;

        Optional<CustomerLog> logDetails = customerLogRepository.findByThreadName(threadName);
        int retrievalRate = configDetails.getCustomer_retrieval_rate();

        if(countBook<=retrievalRate){
            if(logDetails.isPresent()) {
                CustomerLog customerLog = logDetails.get();
                if (Duration.between(customerLog.getPreviousLogDateTime(), LocalDateTime.now()).toMinutes() < 60) {// 1 mean by hour
                    int earlierIssueCount = customerLog.getPrevious_bookingCount();
                    canBookCount = Math.max(0, retrievalRate - earlierIssueCount);
                    if (canBookCount < countBook) {
                        System.out.println("Only can issue : " + canBookCount + " tickets");
                        return 0;
                    } else {
                        customerLog.setPrevious_bookingCount(countBook + earlierIssueCount);
                        customerLogRepository.save(customerLog);

                    }
                } else {
                    customerLog.setPreviousLogDateTime(LocalDateTime.now());
                    customerLog.setPrevious_bookingCount(countBook);
                    customerLogRepository.save(customerLog);

                }
            }else{
                CustomerLog newLog = new CustomerLog();
                newLog.setCustomer(customerDetails);
                newLog.setThreadName(threadName);
                newLog.setPreviousLogDateTime(LocalDateTime.now());
                newLog.setPrevious_bookingCount(countBook);
                customerLogRepository.save(newLog);

            }
            return countBook;

        }else{
            System.out.println("Only one ticket can issue: "+retrievalRate+" tickets.");
            return 0;
        }


    }

    public void bookingFromTicketPool(Customer customer) {

        // Check if the Customer exists by ID
        if (customer != null) {
            // Check if the customer already exists in the database
            Optional<Customer> existingCustomer = customerRegistrationRepository.findByEmail(customer.getEmail());

            if (existingCustomer.isPresent()) {
                // Use the existing customer from the database
                customer = existingCustomer.get();

                // Find the first ticket with NULL customer_id
                List<TicketPool> ticketPoolList = ticketPoolRepository.findFirstByCustomerIsNullOrderByIssueDateTimeAsc();

                if (!ticketPoolList.isEmpty()) {
                    TicketPool ticketDetail = ticketPoolList.get(0);  // Get the first ticket in the list

                    ticketDetail.setCustomer(customer);  // Assign the customer to the ticket
                    ticketDetail.setPurchaseDateTime(LocalDateTime.now());  // Set the purchase time

                    // Save the updated ticket back to the database
                    ticketPoolRepository.save(ticketDetail);
                    System.out.println("Ticket updated with customer.");
                } else {
                    // If no ticket with NULL customer_id is found, throw an exception
                    throw new RuntimeException("No tickets available for booking");
                }

            } else {
                // If customer not found, you can either save the new customer or handle the case appropriately
                System.out.println("Customer not found, not updating TicketPool.");
            }
        }




    }

    @Transactional
    public synchronized int setTicketPollConfig(int ticketsBooked ){
        Configuration configuration = configRepository.findById(1).orElse(null);
        if (configuration != null) {
            int availableTickets = configuration.getTicketsAvailable();
            // Update the ticketsReleased field
            configuration.setTicketsAvailable(availableTickets-ticketsBooked);

            // Save the updated entity back to the database
            configRepository.save(configuration);

            return configuration.getTicketsAvailable();
        }
        return 0;

    }


    /*

    private final int TICKET_POOL_ID = 1;

    public void editAvailableTicket(){
        long numOfAvailableTickets = ticketPoolRepository.countNullCustomer();
        Optional<Configuration> ticketPool = configRepository.findById(TICKET_POOL_ID);
        if(ticketPool.isPresent()){
            Configuration ticketPoolConfig = ticketPool.get();
            ticketPoolConfig.setTicketsAvailable((int)numOfAvailableTickets);
            configRepository.save(ticketPoolConfig); // Ensure save the updated ticket pool
        }else {
            // If no TicketPool with ID 1 exists, you might want to handle this case (e.g., create a new record)
            throw new RuntimeException("TicketPool record with ID "+TICKET_POOL_ID+" not found");
        }



    }

    */
}
