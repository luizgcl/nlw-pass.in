package br.com.luizgcl.passin.repositories;

import br.com.luizgcl.passin.domain.checkin.CheckIn;
import br.com.luizgcl.passin.domain.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CheckInRepository extends JpaRepository<CheckIn, Integer> {

    Optional<CheckIn> findByAttendeeId(String attendeeId);
}
