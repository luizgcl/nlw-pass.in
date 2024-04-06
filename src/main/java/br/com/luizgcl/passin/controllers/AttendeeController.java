package br.com.luizgcl.passin.controllers;

import br.com.luizgcl.passin.dto.attendee.AttendeeBadgeResponseDTO;
import br.com.luizgcl.passin.services.AttendeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/attendees")
@RequiredArgsConstructor
public class AttendeeController {

    private final AttendeeService attendeeService;

    @GetMapping("/{id}/badge")
    public ResponseEntity<AttendeeBadgeResponseDTO> getAttendeeBadge(@PathVariable String id, UriComponentsBuilder uriComponentsBuilder) {
        var attendeeBadge = this.attendeeService.getAttendeeBadge(id, uriComponentsBuilder);

        return ResponseEntity.ok(attendeeBadge);
    }

    @PostMapping("/{id}/check-in")
    public ResponseEntity doCheckIn(@PathVariable String id, UriComponentsBuilder uriComponentsBuilder) {
        this.attendeeService.checkInAttendee(id);

        var uri = uriComponentsBuilder
                .path("/attendees/{id}/badge")
                .buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(uri).build();
    }
}
