package com.example.Ticket.Booking.System.repository;

import com.example.Ticket.Booking.System.model.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfigRepository extends JpaRepository<Configuration, Integer> {

    @Query("SELECT v.vendor_release_rate FROM Configuration v WHERE v.id = :id")
    Optional<Integer> findVendorReleaseRateById(@Param("id") Integer id);


}
