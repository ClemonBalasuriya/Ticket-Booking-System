package com.example.Ticket.Booking.System.service;

import com.example.Ticket.Booking.System.model.*;
import com.example.Ticket.Booking.System.repository.ConfigRepository;
import com.example.Ticket.Booking.System.repository.CustomerLogRepository;
import com.example.Ticket.Booking.System.repository.CustomerRegistrationRepository;
import com.example.Ticket.Booking.System.repository.TicketPoolRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private TicketPoolRepository ticketPoolRepository;

    @Autowired
    private ConfigRepository configRepository;

    @Autowired
    private CustomerLogRepository customerLogRepository;

    @Autowired
    private CustomerRegistrationRepository customerRegistrationRepository;

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
    public void bookTicket(Customer customer, int numOfTickets){
        // Create a TicketBookingTask with customer, number of tickets, and the current service
        TicketBookingRequest task = new TicketBookingRequest(customer,numOfTickets,this);
        Thread thread = new Thread(task, customer.getEmail());
        thread.start();



    }

    public int getCountOfTicketCan(String threadName){
        Optional<CustomerLog> logDetails = customerLogRepository.findByThreadName(threadName);
        int rate = configRepository.findById(TICKET_POOL_ID)
                .orElseThrow(() -> new RuntimeException("Configuration not found"))
                .getCustomer_retrieval_rate();

        if(logDetails.isPresent()){
            CustomerLog customerLog = logDetails.get();
            if (Duration.between(customerLog.getPreviousLogDateTime(), LocalDateTime.now()).toHours() < 1) {// 1 mean by hour
                return Math.max(0, rate - customerLog.getPrevious_bookingCount());
            }
        }
        return rate;



    }





}
