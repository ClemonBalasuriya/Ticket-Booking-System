package com.example.Ticket.Booking.System.repository;

import com.example.Ticket.Booking.System.model.CustomerLog;
import com.example.Ticket.Booking.System.model.VendorLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VendorLogRepository  extends JpaRepository<VendorLog, Integer>  {

    // Query to get VendorLogRepository by threadName
    @Query("SELECT vl FROM VendorLog vl WHERE vl.threadName = :threadName")
    Optional<VendorLog> findByThreadName(String threadName);
}
