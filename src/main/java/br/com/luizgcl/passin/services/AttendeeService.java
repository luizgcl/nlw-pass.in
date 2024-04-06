package br.com.luizgcl.passin.services;

import br.com.luizgcl.passin.domain.attendee.Attendee;
import br.com.luizgcl.passin.domain.attendee.exceptions.AttendeeIsAlreadyRegisteredException;
import br.com.luizgcl.passin.domain.attendee.exceptions.AttendeeNotFoundException;
import br.com.luizgcl.passin.domain.checkin.CheckIn;
import br.com.luizgcl.passin.dto.attendee.AttendeeBadgeResponseDTO;
import br.com.luizgcl.passin.dto.attendee.AttendeeDetailsDTO;
import br.com.luizgcl.passin.dto.attendee.AttendeesListResponseDTO;
import br.com.luizgcl.passin.dto.attendee.AttendeeBadgeDTO;
import br.com.luizgcl.passin.repositories.AttendeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendeeService {

    private final AttendeeRepository attendeeRepository;
    private final CheckInService checkInService;

    public List<Attendee> getAllAttendeesFromEvent(String eventId) {
        return this.attendeeRepository.findByEventId(eventId);
    }

    public AttendeesListResponseDTO getEventsAttendees(String eventId) {
        var attendeeList = this.getAllAttendeesFromEvent(eventId);
        var attendeeDetailsList = attendeeList.stream().map(attendee -> {
            var checkIn = this.checkInService.getCheckInFromAttendee(attendee.getId());
            LocalDateTime checkedInAt = checkIn.<LocalDateTime>map(CheckIn::getCreatedAt).orElse(null);

            return new AttendeeDetailsDTO(attendee.getId(), attendee.getName(), attendee.getEmail(), attendee.getCreatedAt(), checkedInAt);
        }).toList();

        return new AttendeesListResponseDTO(attendeeDetailsList);
    }

    public Attendee registerAttendee(Attendee attendee) {
        this.attendeeRepository.save(attendee);
        return attendee;
    }

    public void verifyAttendeeSubscription(String attendeeEmail, String eventId) {
        var isAttendeeRegistered = this.attendeeRepository.findByEventIdAndEmail(eventId, attendeeEmail);

        if (isAttendeeRegistered.isPresent()) {
            throw new AttendeeIsAlreadyRegisteredException();
        }
    }

    public AttendeeBadgeResponseDTO getAttendeeBadge(String attendeeId, UriComponentsBuilder uriComponentsBuilder) {
        var attendee = this.getAttendee(attendeeId);

        var uri = uriComponentsBuilder
                .path("/attendees/{attendeeId}/check-in")
                .buildAndExpand(attendeeId)
                .toUri()
                .toString();

        return new AttendeeBadgeResponseDTO(
                new AttendeeBadgeDTO(
                        attendee.getName(),
                        attendee.getEmail(),
                        uri,
                        attendee.getEvent().getId()
                )
        );
    }

    public void checkInAttendee(String attendeeId) {
        var attendee = this.getAttendee(attendeeId);

        this.checkInService.doCheckIn(attendee);
    }

    private Attendee getAttendee(String attendeeId) {
        return this.attendeeRepository.findById(attendeeId)
                .orElseThrow(() -> new AttendeeNotFoundException(attendeeId));
    }
}
