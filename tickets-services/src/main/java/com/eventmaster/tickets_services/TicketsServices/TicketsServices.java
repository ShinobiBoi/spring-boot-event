package com.eventmaster.tickets_services.TicketsServices;

import com.eventmaster.tickets_services.model.Ticket;
import com.eventmaster.tickets_services.repository.TicketsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketsServices {

    @Autowired
    private TicketsRepo repo;

    public Ticket createTicket(Ticket ticket) {
        return repo.save(ticket);
    }

    public List<Ticket> getAllTickets() {
        return repo.findAll();
    }

    public Ticket getTicketById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public List<Ticket> getTicketsByUserId(Long userId) {
        return repo.findByUserId(userId);
    }

    public List<Ticket> getTicketsByEventId(Long eventId) {
        return repo.findByEventId(eventId);
    }

    public void deleteTicketsByEventId(Long eventId) {
        List<Ticket> tickets = repo.findByEventId(eventId);
        repo.deleteAll(tickets);
    }

    public void deleteTicket(Long id) {
        repo.deleteById(id);
    }
}