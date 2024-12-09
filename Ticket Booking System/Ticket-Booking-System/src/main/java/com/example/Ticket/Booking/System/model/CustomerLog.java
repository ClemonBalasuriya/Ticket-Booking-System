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
public class CustomerLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String threadName;


    private LocalDateTime previousLogDateTime;
    private int previous_bookingCount;


    @OneToOne
    @JoinColumn(name = "vendor_id", nullable = false)
    private Customer customer;
}
