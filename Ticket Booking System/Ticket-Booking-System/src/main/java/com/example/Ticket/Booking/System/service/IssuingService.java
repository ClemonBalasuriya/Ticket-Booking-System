package com.example.Ticket.Booking.System.service;

import com.example.Ticket.Booking.System.model.*;
import com.example.Ticket.Booking.System.repository.*;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Data
@Service
public class IssuingService {

    int val =0;

    @Autowired
    private ConfigRepository configRepository;

    @Autowired
    private ConfigService configService;

    @Autowired
    private TicketRepository ticketRepository;


    @Autowired
    private BookingService bookingService;

    @Autowired
    private TicketPoolRepository ticketPoolRepository;

    @Autowired
    private VendorLogRepository vendorLogRepository;

    @Autowired
    private VendorRegistrationRepository vendorRegistrationRepository;

    private final int TICKET_POOL_ID = 1;

    @Transactional
    public void issueTicket(Vendor vendor, int numOfTickets){

        TicketReleasingRequest task = new TicketReleasingRequest(vendor,numOfTickets,this);
        Thread thread = new Thread(task, vendor.getEmail());
        thread.start();


    }

    public Configuration getTicketPoolConfig() {
        return  configService.readConfigDataBase();
    }

    public int setTicketPollConfig(int ticketsReleased ){
        Configuration configuration = configRepository.findById(1).orElse(null);
        if (configuration != null) {
            int releasedCount = configuration.getTicketsReleased();
            // Update the ticketsReleased field
            configuration.setTicketsReleased(releasedCount+ticketsReleased);

            // Save the updated entity back to the database
            configRepository.save(configuration);
            return configuration.getTicketsReleased();
        }
        return 0;

    }

    public Vendor getVendorDetails(Vendor vendor) {

        if (vendor != null) {

            String vendorEmail = vendor.getEmail();
            Optional<Vendor> existingVendor = vendorRegistrationRepository.findByEmail(vendorEmail);
            if (existingVendor.isPresent()) {
                return existingVendor.get();

            } else {
                System.out.println("Vendor not found, not updating TicketPool.");
            }
        }
        return null;
    }

    public  int getCountOfTicketCan(String threadName,int countIssue,Vendor vendor,IssuingService issuingService){
        Configuration configDetails =  issuingService.getTicketPoolConfig();
        Vendor vendorDetails = getVendorDetails(vendor);
        if(vendorDetails == null){return 0;}
        int canIssueCount =0;
        Optional<VendorLog> logDetails = vendorLogRepository.findByThreadName(threadName);
        //Optional<Integer> configDetail = configRepository.findVendorReleaseRateById(Integer.valueOf("1"));
        //int rate = configDetails.get();
        int releaseRate = configDetails.getVendor_release_rate();

        if(countIssue<=releaseRate){
            if(logDetails.isPresent()){
                VendorLog vendorLog = logDetails.get();
                if (Duration.between(vendorLog.getPreviousLogDateTime(), LocalDateTime.now()).toMinutes() < 60) {// 1 mean by hour

                    int earlierIssueCount = vendorLog.getPrevious_bookingCount();
                    canIssueCount = Math.max(0, releaseRate -earlierIssueCount );

                    if(canIssueCount<countIssue){
                        System.out.println("Only can issue : "+canIssueCount+" tickets");
                        return 0;
                    }else{
                        vendorLog.setPrevious_bookingCount(countIssue+earlierIssueCount);
                        vendorLogRepository.save(vendorLog);

                    }

                }else{
                    vendorLog.setPreviousLogDateTime(LocalDateTime.now());
                    vendorLog.setPrevious_bookingCount(countIssue);
                    vendorLogRepository.save(vendorLog);

                }

            }else{
                VendorLog newLog = new VendorLog();
                newLog.setVendor(vendorDetails);
                newLog.setThreadName(threadName);
                newLog.setPreviousLogDateTime(LocalDateTime.now());
                newLog.setPrevious_bookingCount(countIssue);
                vendorLogRepository.save(newLog);

            }
            return countIssue;


        }else{
            System.out.println("Only one ticket can issue: "+releaseRate+" tickets.");
            return 0;
        }


    }

    public void releasingTickets(Vendor vendor){
        if (vendor != null) {
            Optional<Vendor> existingCustomer = vendorRegistrationRepository.findByEmail(vendor.getEmail());
            if (existingCustomer.isPresent()) {
                vendor = existingCustomer.get();
                TicketPool ticketPool= new TicketPool();
                ticketPool.setIssuer(vendor);
                ticketPool.setTicket(new Ticket(1,"Film",20));
                ticketPool.setIssueDateTime(LocalDateTime.now());
                ticketPoolRepository.save(ticketPool);
                System.out.println("Releasing TicketPool");
            }

        }
    }













    /*
    public void editNumOfReleasedTicketAndAvailableTickets(){
        long numOfReleasedTickets = ticketPoolRepository.countTicketsInPool();
        Optional<Configuration> ticketPool = configRepository.findById(TICKET_POOL_ID);
        if(ticketPool.isPresent()){
            Configuration ticketPoolConfig = ticketPool.get();
            ticketPoolConfig.setTicketsReleased((int)numOfReleasedTickets) ;
            configRepository.save(ticketPoolConfig); // Ensure save the updated ticket pool
        }else {
            // If no TicketPool with ID 1 exists, you might want to handle this case (e.g., create a new record)
            throw new RuntimeException("TicketPool record with ID "+TICKET_POOL_ID+" not found");
        }
        bookingService.editAvailableTicket();

    }


    public int onlyCanAddTicket(){
        return (configRepository.findById(1).orElseThrow().getTotalTickets() - configService.getTotalReleaseTickets());
    }
    */



}
