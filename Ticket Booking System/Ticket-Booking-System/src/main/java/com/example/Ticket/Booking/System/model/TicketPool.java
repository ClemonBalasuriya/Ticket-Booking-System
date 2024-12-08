package com.example.Ticket.Booking.System.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity     //create table in database

public class TicketPool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDateTime issueDateTime;
    private LocalDateTime purchaseDateTime;

    @ManyToOne // Many tickets can belong to the same pool
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;

    @ManyToOne // Indicates a many-to-one relationship
    @JoinColumn(name = "vendor_id", nullable = false) // Specifies the foreign key column
    private Vendor issuer;

    @ManyToOne // Indicates a many-to-one relationship
    @JoinColumn(name = "customer_id", nullable = false) // Specifies the foreign key column
    private Customer customer;
}
