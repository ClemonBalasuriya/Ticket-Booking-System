package com.example.Ticket.Booking.System.controller;

import com.example.Ticket.Booking.System.model.Configuration;
import com.example.Ticket.Booking.System.model.Customer;
import com.example.Ticket.Booking.System.model.TicketBookingRequest;
import com.example.Ticket.Booking.System.repository.TicketPoolRepository;
import com.example.Ticket.Booking.System.service.BookingService;
import com.example.Ticket.Booking.System.service.ConfigService;
import com.example.Ticket.Booking.System.service.CustomerRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @Autowired
    private ConfigService configService;

    @PostMapping("/ticketbooking")
    @CrossOrigin(origins = "http://localhost:4200")
    public int bookTicket(@RequestBody TicketBookingRequest request){
        //configService.updateAvailableTickets();
        try {
            // Call the booking service to process the ticket booking
            return bookingService.bookTicket(request.getCustomer(), request.getNumOfTickets());
        } catch (Exception e) {
            System.out.println("Error occurred in BookingController");
        }
        return 0;

    }

   /* @GetMapping ("/ticketbookingdata")
    @CrossOrigin(origins = "http://localhost:4200")
    public int getbookTicketdata(){
        return bookingService.getVal();
    }*/





}
