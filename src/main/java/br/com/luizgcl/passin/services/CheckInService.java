package br.com.luizgcl.passin.services;

import br.com.luizgcl.passin.domain.attendee.Attendee;
import br.com.luizgcl.passin.domain.checkin.CheckIn;
import br.com.luizgcl.passin.domain.checkin.exceptions.CheckInAlreadyExistsException;
import br.com.luizgcl.passin.repositories.CheckInRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheckInService {

    private final CheckInRepository checkInRepository;

    public Optional<CheckIn> getCheckInFromAttendee(String attendeeId) {
        return this.checkInRepository.findByAttendeeId(attendeeId);
    }

    public CheckIn doCheckIn(Attendee attendee) {
        this.verifyCheckInExists(attendee.getId());

        CheckIn checkIn = new CheckIn();

        checkIn.setAttendee(attendee);
        checkIn.setCreatedAt(LocalDateTime.now());

        this.checkInRepository.save(checkIn);

        return checkIn;
    }

    private void verifyCheckInExists(String attendeeId) {
        var isCheckedIn = this.getCheckInFromAttendee(attendeeId);

        if (isCheckedIn.isPresent()) {
            throw new CheckInAlreadyExistsException();
        }
    }
}
