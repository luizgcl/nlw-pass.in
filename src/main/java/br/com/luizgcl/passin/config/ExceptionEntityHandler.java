package br.com.luizgcl.passin.config;

import br.com.luizgcl.passin.domain.attendee.exceptions.AttendeeIsAlreadyRegisteredException;
import br.com.luizgcl.passin.domain.attendee.exceptions.AttendeeNotFoundException;
import br.com.luizgcl.passin.domain.checkin.exceptions.CheckInAlreadyExistsException;
import br.com.luizgcl.passin.domain.event.exceptions.EventIsFullException;
import br.com.luizgcl.passin.domain.event.exceptions.EventNotFoundException;
import br.com.luizgcl.passin.dto.exceptions.ExceptionResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionEntityHandler {

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity handleEventNotFound(EventNotFoundException exception) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(AttendeeNotFoundException.class)
    public ResponseEntity handleAttendeeNotFound(AttendeeNotFoundException exception) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(AttendeeIsAlreadyRegisteredException.class)
    public ResponseEntity handleAttendeeNotFound(AttendeeIsAlreadyRegisteredException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler(CheckInAlreadyExistsException.class)
    public ResponseEntity handleAttendeeNotFound(CheckInAlreadyExistsException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler(EventIsFullException.class)
    public ResponseEntity handleAttendeeNotFound(EventIsFullException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponseDTO(exception.getMessage()));
    }
}
