package com.example.Ticket.Booking.System.service;

import com.example.Ticket.Booking.System.model.Ticket;
import com.example.Ticket.Booking.System.model.TicketPool;
import com.example.Ticket.Booking.System.repository.TicketPoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketPoolService {
    @Autowired
    private TicketPoolRepository ticketPoolRepository;

    public TicketPool addTicket(TicketPool ticket) {
        return ticketPoolRepository.save(ticket);
    }

    public TicketPool findTicketInPool(int id) {
        return ticketPoolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found with ID: " + id));
    }

    // Get count of tickets
    public long getTicketCountInPool() {
        return ticketPoolRepository.countTicketsInPool();
    }

    public long getAvailableTicketsInPool() {
        return ticketPoolRepository.countNonNullIssueDateTime();
    }



}
