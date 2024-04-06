package br.com.luizgcl.passin.services;

import br.com.luizgcl.passin.domain.attendee.Attendee;
import br.com.luizgcl.passin.domain.event.Event;
import br.com.luizgcl.passin.domain.event.exceptions.EventIsFullException;
import br.com.luizgcl.passin.domain.event.exceptions.EventNotFoundException;
import br.com.luizgcl.passin.dto.attendee.AttendeeIdDTO;
import br.com.luizgcl.passin.dto.attendee.AttendeeRequestDTO;
import br.com.luizgcl.passin.dto.event.EventIdDTO;
import br.com.luizgcl.passin.dto.event.EventRequestDTO;
import br.com.luizgcl.passin.dto.event.EventResponseDTO;
import br.com.luizgcl.passin.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final AttendeeService attendeeService;

    public EventResponseDTO getEventDetails(String id) {
        Event event = this.getEventById(id);
        List<Attendee> attendeeList = this.attendeeService.getAllAttendeesFromEvent(id);
        return new EventResponseDTO(event, attendeeList.size());
    }

    public EventIdDTO createEvent(EventRequestDTO eventDTO) {
        Event newEvent = new Event();

        newEvent.setTitle(eventDTO.title());
        newEvent.setDetails(eventDTO.details());
        newEvent.setMaximumAttendees(eventDTO.maximumAttendees());

        newEvent.setSlug(slugfy(eventDTO.title()));

        this.eventRepository.save(newEvent);

        return new EventIdDTO(newEvent.getId());
    }

    public AttendeeIdDTO registerAttendeeOnEvent(String eventId, AttendeeRequestDTO attendeeRequest) {
        Event event = this.getEventById(eventId);

        this.attendeeService.verifyAttendeeSubscription(attendeeRequest.email(), eventId);

        List<Attendee> attendeeList = this.attendeeService.getAllAttendeesFromEvent(eventId);

        if (event.getMaximumAttendees() <= attendeeList.size()) {
            throw new EventIsFullException();
        }

        Attendee attendee = new Attendee();

        attendee.setName(attendeeRequest.name());
        attendee.setEmail(attendeeRequest.email());
        attendee.setEvent(event);
        attendee.setCreatedAt(LocalDateTime.now());

        this.attendeeService.registerAttendee(attendee);

        return new AttendeeIdDTO(attendee.getId());
    }

    private Event getEventById(String eventId) {
        return this.eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId));
    }

    private String slugfy(String title) {
        String normalized = Normalizer.normalize(title, Normalizer.Form.NFD);

        return normalized
                .replaceAll("[\\p{InCOMBINING_DIACRITICAL_MARKS}]", "")
                .replaceAll("[^\\w\\s]", "")
                .replaceAll("\\s+", "-")
                .toLowerCase();
    }
}
