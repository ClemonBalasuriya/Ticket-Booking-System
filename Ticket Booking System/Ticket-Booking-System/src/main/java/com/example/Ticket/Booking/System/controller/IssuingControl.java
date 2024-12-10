package com.example.Ticket.Booking.System.controller;

import com.example.Ticket.Booking.System.model.TicketBookingRequest;
import com.example.Ticket.Booking.System.model.TicketReleasingRequest;
import com.example.Ticket.Booking.System.service.BookingService;
import com.example.Ticket.Booking.System.service.ConfigService;
import com.example.Ticket.Booking.System.service.IssuingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IssuingControl {
    @Autowired
    private IssuingService issuingService;

    @Autowired
    private ConfigService configService;

    @PostMapping("/ticketrelease")
    @CrossOrigin(origins = "http://localhost:4200")
    public int issueTicket(@RequestBody TicketReleasingRequest request){
        configService.updateReleasedTicketsCount();
        try {
            issuingService.issueTicket(request.getVendor(), request.getNumOfTickets());

            // Return a success response if everything goes well
            return configService.updateReleasedTicketsCount();
        } catch (Exception e) {
            // Return an error response if something goes wrong
            return configService.updateReleasedTicketsCount();
        }

    }
}
