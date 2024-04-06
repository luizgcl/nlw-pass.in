package br.com.luizgcl.passin.controllers;

import br.com.luizgcl.passin.dto.attendee.AttendeeRequestDTO;
import br.com.luizgcl.passin.dto.attendee.AttendeesListResponseDTO;
import br.com.luizgcl.passin.dto.event.EventIdDTO;
import br.com.luizgcl.passin.dto.event.EventRequestDTO;
import br.com.luizgcl.passin.dto.event.EventResponseDTO;
import br.com.luizgcl.passin.services.AttendeeService;
import br.com.luizgcl.passin.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final AttendeeService attendeeService;

    @GetMapping("/{id}/attendees")
    public ResponseEntity<AttendeesListResponseDTO> getEventAttendees(@PathVariable String id) {
        AttendeesListResponseDTO response = this.attendeeService.getEventsAttendees(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDTO> getEvent(@PathVariable("id") String eventId) {
        EventResponseDTO response = this.eventService.getEventDetails(eventId);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<EventIdDTO> createEvent(@RequestBody EventRequestDTO eventRequestDTO, UriComponentsBuilder uriComponentsBuilder) {
        EventIdDTO response = this.eventService.createEvent(eventRequestDTO);

        var uri = uriComponentsBuilder.path("/events/{id}")
                .buildAndExpand(response.eventId())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @PostMapping("/{id}/attendees")
    public ResponseEntity registerAttendee(@PathVariable String id, @RequestBody AttendeeRequestDTO attendeeRequestDTO, UriComponentsBuilder uriComponentsBuilder) {
        var attendeeResponse = this.eventService.registerAttendeeOnEvent(id, attendeeRequestDTO);

        var uri = uriComponentsBuilder.path("/attendees/{id}/badge")
                .buildAndExpand(attendeeResponse.id())
                .toUri();

        return ResponseEntity.created(uri).body(attendeeResponse);
    }
}
