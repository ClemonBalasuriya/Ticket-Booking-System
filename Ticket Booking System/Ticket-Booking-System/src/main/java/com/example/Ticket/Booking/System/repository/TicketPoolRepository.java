package com.example.Ticket.Booking.System.repository;

import com.example.Ticket.Booking.System.model.TicketPool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketPoolRepository extends JpaRepository<TicketPool, Integer> {

    // Counting tickets in the TicketPool (based on TicketPool entity)
    @Query("SELECT COUNT(tp) FROM TicketPool tp")
    long countTicketsInPool();

    // Counting non-null issueDateTime values in the TicketPool entity
    @Query("SELECT COUNT(tp.issueDateTime) FROM TicketPool tp WHERE tp.issueDateTime IS  NULL")
    long countNonNullIssueDateTime();
}
