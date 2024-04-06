package br.com.luizgcl.passin.domain.attendee.exceptions;

public class AttendeeIsAlreadyRegisteredException extends RuntimeException {

    public AttendeeIsAlreadyRegisteredException() {
        super("Attendee is already registered");
    }
}
