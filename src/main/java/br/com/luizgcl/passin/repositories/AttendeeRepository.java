package br.com.luizgcl.passin.repositories;

import br.com.luizgcl.passin.domain.attendee.Attendee;
import br.com.luizgcl.passin.domain.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AttendeeRepository extends JpaRepository<Attendee, String> {

    List<Attendee> findByEventId(String eventId);

    Optional<Attendee> findByEventIdAndEmail(String eventId, String attendeeEmail);
}
