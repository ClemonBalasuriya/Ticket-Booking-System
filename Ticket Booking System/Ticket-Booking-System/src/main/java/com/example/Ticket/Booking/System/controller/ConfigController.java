package com.example.Ticket.Booking.System.controller;

import com.example.Ticket.Booking.System.model.Configuration;
import com.example.Ticket.Booking.System.repository.TicketPoolRepository;
import com.example.Ticket.Booking.System.service.ConfigService;
import com.example.Ticket.Booking.System.service.TicketPoolService;
import org.hibernate.engine.config.spi.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ConfigController {

    @Autowired
    private ConfigService configurationService;

    @GetMapping("")
    @CrossOrigin(origins = "http://localhost:4200")
    public Configuration getTicketsSummary() {
        configurationService.updateAvailableTickets();

        int totalTickets = configurationService.getTotalTickets();
        int totalTicketsReleased = configurationService.getTotalReleaseTickets();
        int totalTicketsAvailable = configurationService.getTotalAvailableTickets();
        int customer_retrieval_rate = configurationService.getCustomer_retrieval_rate();
        int vendor_release_rate = configurationService.getVendor_release_rate();
        return (new Configuration(1,totalTickets,totalTicketsReleased,totalTicketsAvailable,customer_retrieval_rate,vendor_release_rate) );

    }

    @PostMapping("/changeconfig")
    @CrossOrigin(origins = "http://localhost:4200")
    public Configuration changeConfig(@RequestBody Configuration configuration) throws Exception {

        return configurationService.updateTicketConfig(configuration.getTotalTickets(),configuration.getCustomer_retrieval_rate(),configuration.getVendor_release_rate());
    }

}
