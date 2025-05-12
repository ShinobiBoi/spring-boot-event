package com.eventmaster.event_services.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "my_events")
public class Event {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer eventId;

    private String organizerName ;

    private String title ;

    private String description ;

    private String venue;

    private Date eventDate;

    private Double ticketPrice;

    private Integer ticketsLeft;

    private Integer participantsSubmitted ;

    private boolean submitted;

    private Integer userId;



}