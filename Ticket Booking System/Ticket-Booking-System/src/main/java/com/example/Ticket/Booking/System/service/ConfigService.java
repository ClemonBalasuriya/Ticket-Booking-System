package com.example.Ticket.Booking.System.service;

import com.example.Ticket.Booking.System.model.Configuration;
import com.example.Ticket.Booking.System.repository.ConfigRepository;
import com.example.Ticket.Booking.System.repository.CustomerRegistrationRepository;
import com.example.Ticket.Booking.System.repository.TicketPoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigService {

    @Autowired
    private ConfigRepository configRepository;

    @Autowired
    private TicketPoolRepository ticketPoolRepository;


    // Method to update configuration values by ID
    public Configuration updateTicketConfig(int maxTickets, int customerRetrievalRate, int vendorReleaseRate) {
        // Retrieve the configuration by ID
        Configuration configuration = configRepository.findById(1)
                .orElseThrow(() -> new RuntimeException("Configuration not found with ID: " + 1));

        // Update the configuration fields
        configuration.setTotalTickets(maxTickets);
        configuration.setCustomer_retrieval_rate(customerRetrievalRate);
        configuration.setVendor_release_rate(vendorReleaseRate);

        // Save the updated configuration back to the database
        return configRepository.save(configuration);
    }

    // read the total tickets count
    public int getTotalTickets() {
        return configRepository.findById(1).map(Configuration::getTotalTickets).orElse(0);
    }

    // read the total release tickets count
    public int getTotalReleaseTickets() {
        return configRepository.findById(1).map(Configuration::getTicketsReleased).orElse(0);
    }

    // read the total available tickets count
    public int getTotalAvailableTickets() {
        return configRepository.findById(1).map(Configuration::getTicketsAvailable).orElse(0);
    }
    // read the Customer_retrieval_rate
    public int getCustomer_retrieval_rate() {
        return configRepository.findById(1).map(Configuration::getCustomer_retrieval_rate).orElse(0);
    }
    // read the Vendor_release_rate
    public int getVendor_release_rate() {
        return configRepository.findById(1).map(Configuration::getVendor_release_rate).orElse(0);
    }

    //update availableConfigTable
    public int updateAvailableTickets(){

        return update("AvailableTickets");

    }
    public int updateReleasedTicketsCount(){

        return update("ReleasedTickets");

    }
    public int update(String name){
        long count = ticketPoolRepository.countNullCustomer();
        long tolCount = ticketPoolRepository.countTicketsInPool();
        configRepository.findById(1).orElseThrow().setTicketsReleased((int)count);
        configRepository.findById(1).orElseThrow().setTicketsReleased((int)tolCount);
        if(name.equals("AvailableTickets")){
            return (int)count;
        }else return (int)tolCount;

    }
}
