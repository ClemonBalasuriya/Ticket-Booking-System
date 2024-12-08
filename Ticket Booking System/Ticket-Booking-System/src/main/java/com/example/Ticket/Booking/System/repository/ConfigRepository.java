package com.example.Ticket.Booking.System.repository;

import com.example.Ticket.Booking.System.model.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigRepository extends JpaRepository<Configuration, Integer> {
}
