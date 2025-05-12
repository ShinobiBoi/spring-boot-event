package com.eventmaster.event_services.services;

import com.eventmaster.event_services.model.Event;
import com.eventmaster.event_services.repository.EventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepo eventRepo;

    // Create or Update
    public Event saveEvent(Event event) {
        return eventRepo.save(event);
    }

    // Read all
    public List<Event> getAllEvents() {
        return eventRepo.findAll();
    }

    // Read by ID
    public Event getEventById(Integer id) {
        return eventRepo.findById(id).orElse(null);
    }

    public List<Event> getEventByUserID(Integer id){
        return eventRepo.findByUserId(id);
    }


    // Update
    public Event updateEvent(Integer id, Event updatedEvent) {
        Event existingEvent = eventRepo.findById(id).orElse(null);
        if (existingEvent == null) {
            return null;
        }

        existingEvent.setOrganizerName(updatedEvent.getOrganizerName());
        existingEvent.setTitle(updatedEvent.getTitle());
        existingEvent.setDescription(updatedEvent.getDescription());
        existingEvent.setVenue(updatedEvent.getVenue());
        existingEvent.setEventDate(updatedEvent.getEventDate());
        existingEvent.setTicketPrice(updatedEvent.getTicketPrice());
        existingEvent.setTicketsLeft(updatedEvent.getTicketsLeft());
        existingEvent.setParticipantsSubmitted(updatedEvent.getParticipantsSubmitted());
        existingEvent.setSubmitted(updatedEvent.isSubmitted());

        return eventRepo.save(existingEvent);
    }

    // Delete
    public Event deleteEvent(Integer id) {
        Event existingEvent = eventRepo.findById(id).orElse(null);
        if (existingEvent == null) {
            return null;
        }

        eventRepo.deleteById(id);
        return existingEvent;
    }
}

