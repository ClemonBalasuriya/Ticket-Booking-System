package com.example.Ticket.Booking.System.service;

import com.example.Ticket.Booking.System.model.*;
import com.example.Ticket.Booking.System.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class IssuingService {
    @Autowired
    private ConfigService configService;

    @Autowired
    private BookingService bookingService;


    @Autowired
    private TicketPoolRepository ticketPoolRepository;

    @Autowired
    private ConfigRepository configRepository;

    @Autowired
    private VendorLogRepository vendorLogRepository;

    @Autowired
    private VendorRegistrationRepository vendorRegistrationRepository;

    private final int TICKET_POOL_ID = 1;

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

    public void issueForTicketPool(Vendor vendor) {

        if (vendor != null) {
            Optional<Vendor> existingVendor = vendorRegistrationRepository.findByEmail("avishkaclemon@gmail.com");

            if (existingVendor.isPresent()) {

                vendor = existingVendor.get();


                TicketPool ticket = new TicketPool();
                ticket.setIssuer(vendor);
                ticket.setIssueDateTime(LocalDateTime.now());
                ticket.setId(1);
                System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
                ticketPoolRepository.save(ticket);
                System.out.println("Ticket added to the pool.");


            } else {
                // If customer not found, you can either save the new customer or handle the case appropriately
                System.out.println("Vendor not found, not updating TicketPool.");
            }
        }

    }

    @Transactional
    public void issueTicket(Vendor vendor, int numOfTickets){


        TicketReleasingRequest task = new TicketReleasingRequest(vendor,numOfTickets,this);
        Thread thread = new Thread(task,vendor.getEmail());
        thread.start();

    }

    public int getCountOfTicketCan(String threadName){
        Optional<VendorLog> logDetails = vendorLogRepository.findByThreadName(threadName);
        int rate = configRepository.findById(TICKET_POOL_ID)
                .orElseThrow(() -> new RuntimeException("Configuration not found"))
                .getVendor_release_rate();

        if(logDetails.isPresent()){
            VendorLog vendorLog = logDetails.get();
            if (Duration.between(vendorLog.getPreviousLogDateTime(), LocalDateTime.now()).toHours() < 1) {// 1 mean by hour
                return Math.max(0, rate - vendorLog.getPrevious_bookingCount());
            }
        }
        return rate;

    }

    public int onlyCanAddTicket(){
        return (configRepository.findById(1).orElseThrow().getTotalTickets() - configService.getTotalReleaseTickets());
    }

}
