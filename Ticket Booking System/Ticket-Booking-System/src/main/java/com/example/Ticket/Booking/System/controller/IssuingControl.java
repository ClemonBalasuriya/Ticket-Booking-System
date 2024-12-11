package com.example.Ticket.Booking.System.controller;

import com.example.Ticket.Booking.System.model.Configuration;
import com.example.Ticket.Booking.System.model.TicketBookingRequest;
import com.example.Ticket.Booking.System.model.TicketReleasingRequest;
import com.example.Ticket.Booking.System.service.BookingService;
import com.example.Ticket.Booking.System.service.ConfigService;
import com.example.Ticket.Booking.System.service.IssuingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class IssuingControl {
    @Autowired
    private IssuingService issuingService;

    @Autowired
    private ConfigService configService;

    @PostMapping("/ticketreleasing")
    @CrossOrigin(origins = "http://localhost:4200")
    public int issueTicket(@RequestBody TicketReleasingRequest request){
        try {
            issuingService.issueTicket(request.getVendor(), request.getNumOfTickets());
            Configuration config = configService.readConfigDataBase();
            return config != null ? config.getTicketsReleased() : 0; // Always fetch the latest cou
        } catch (Exception e) {
            System.out.println("Error occurred in BookingController");
            return 0;

        }

    }
/*
    @GetMapping("/ticketreleasingdata")
    @CrossOrigin(origins = "http://localhost:4200")
    public int getIssueTicketdata(){

    }*/
}
