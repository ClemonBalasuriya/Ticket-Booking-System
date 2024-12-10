package com.example.Ticket.Booking.System.repository;

import com.example.Ticket.Booking.System.model.Customer;
import com.example.Ticket.Booking.System.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VendorRegistrationRepository extends JpaRepository<Vendor, Integer> {

    public Vendor findByEmailAndPassword(String email, String password);

    @Query("SELECT v FROM Vendor v WHERE v.email = :email")
    Optional<Vendor> findByEmail(@Param("email") String email);
}
