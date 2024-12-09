package com.example.Ticket.Booking.System.repository;

import com.example.Ticket.Booking.System.model.Ticket;
import com.example.Ticket.Booking.System.model.TicketPool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface TicketPoolRepository extends JpaRepository<TicketPool, Integer> {

    // Counting tickets in the TicketPool (based on TicketPool entity)
    @Query("SELECT COUNT(tp) FROM TicketPool tp")
    long countTicketsInPool();

    // Counting non-null issueDateTime values in the TicketPool entity
    @Query("SELECT COUNT(tp.issueDateTime) FROM TicketPool tp WHERE tp.issueDateTime IS  NULL")
    long countNonNullIssueDateTime();

    @Query("SELECT tp FROM TicketPool tp WHERE tp.customer IS NULL ORDER BY tp.issueDateTime ASC")
    ArrayList<TicketPool> findFirstByCustomerIsNullOrderByIssueDateTimeAsc();

    // Query to count the number of NULL values in the customer column
    @Query("SELECT COUNT(tp) FROM TicketPool tp WHERE tp.customer IS NULL")
    long countNullCustomer();

}
