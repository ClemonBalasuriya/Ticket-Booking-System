package com.example.Ticket.Booking.System.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity     //create table in database

public class Configuration {
    @Id
    private int id;
    private int totalTickets;
    private int ticketsReleased;
    private int ticketsAvailable;
    private int customer_retrieval_rate;
    private int vendor_release_rate;

}
