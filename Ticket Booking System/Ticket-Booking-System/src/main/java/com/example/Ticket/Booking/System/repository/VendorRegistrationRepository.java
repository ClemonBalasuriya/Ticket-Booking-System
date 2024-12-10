package com.example.Ticket.Booking.System.repository;

import com.example.Ticket.Booking.System.model.Customer;
import com.example.Ticket.Booking.System.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VendorRegistrationRepository extends JpaRepository<Vendor, Integer> {
    Optional<Vendor> findByEmail(String email);
    public Vendor findByEmailAndPassword(String email, String password);
}
