package com.eventmaster.event_services.repository;

import com.eventmaster.event_services.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepo extends JpaRepository<Event,Integer> {

    List<Event> findByUserId (Integer userId);
}
