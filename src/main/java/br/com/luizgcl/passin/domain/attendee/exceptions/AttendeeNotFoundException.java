package br.com.luizgcl.passin.domain.attendee.exceptions;

public class AttendeeNotFoundException extends RuntimeException {

    public AttendeeNotFoundException(String id) {
        super("Attendee not found with " + id);
    }
}
