package com.example.Ticket.Booking.System.repository;

import com.example.Ticket.Booking.System.model.CustomerLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerLogRepository extends JpaRepository<CustomerLog, Integer> {

    // Query to get CustomerLogRepository by threadName
    @Query("SELECT cl FROM CustomerLog cl WHERE cl.threadName = :threadName")
    Optional<CustomerLog> findByThreadName(String threadName);

}
