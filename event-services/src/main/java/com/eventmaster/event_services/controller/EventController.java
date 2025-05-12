package com.eventmaster.event_services.controller;

import com.eventmaster.event_services.model.Event;
import com.eventmaster.event_services.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    // Create a new event
    @PostMapping
    public Event createEvent(@RequestBody Event event) {
        return eventService.saveEvent(event);
    }

    // Get all events
    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    // Get event by ID
    @GetMapping("/{id}")
    public Event getEventById(@PathVariable Integer id) {
        return eventService.getEventById(id);
    }

    @GetMapping("/user/{userId}")
    public List<Event> getEventByUserId(@PathVariable Integer userId) {
        return eventService.getEventByUserID(userId);
    }

    // Update an event
    @PutMapping("/{id}")
    public Event updateEvent(@PathVariable Integer id, @RequestBody Event event) {
        return eventService.updateEvent(id, event);
    }

    @PutMapping("/register/{id}")
    public ResponseEntity<String> registerEvent(@PathVariable Integer id, @RequestBody Integer numberOfTickets) {

        Event event = eventService.getEventById(id);
        if (event == null) {
            return ResponseEntity.notFound().build();
        }


        if (event.getTicketsLeft() < numberOfTickets) {
            return ResponseEntity.badRequest().body("Not enough tickets available");
        }



        event.setParticipantsSubmitted(event.getParticipantsSubmitted() + numberOfTickets );
        event.setTicketsLeft(event.getTicketsLeft() - numberOfTickets);

        Event updatedEvent = eventService.updateEvent(id, event);

        return ResponseEntity.ok(String.format(
                "Registered successfully! Participants: %d, Tickets left: %d",
                updatedEvent.getParticipantsSubmitted(),
                updatedEvent.getTicketsLeft()
        ));
    }

    // Delete an event
    @DeleteMapping("/{id}")
    public Event deleteEvent(@PathVariable Integer id) {
        return eventService.deleteEvent(id);
    }
}

