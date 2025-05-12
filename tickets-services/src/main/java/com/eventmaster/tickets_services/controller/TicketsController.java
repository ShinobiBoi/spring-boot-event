package com.eventmaster.tickets_services.controller;

import com.eventmaster.tickets_services.TicketsServices.TicketsServices;
import com.eventmaster.tickets_services.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketsController {

    @Autowired
    private TicketsServices service;

    @PostMapping
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket) {
        return ResponseEntity.ok(service.createTicket(ticket));
    }

    @GetMapping
    public List<Ticket> getAllTickets() {
        return service.getAllTickets();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long id) {
        Ticket ticket = service.getTicketById(id);
        return ticket != null ? ResponseEntity.ok(ticket) : ResponseEntity.notFound().build();
    }

    @GetMapping("/user/{userId}")
    public List<Ticket> getTicketsByUser(@PathVariable Long userId) {
        return service.getTicketsByUserId(userId);
    }

    @GetMapping("/event/{eventId}")
    public List<Ticket> getTicketsByEvent(@PathVariable Long eventId) {
        return service.getTicketsByEventId(eventId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        service.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }
}
