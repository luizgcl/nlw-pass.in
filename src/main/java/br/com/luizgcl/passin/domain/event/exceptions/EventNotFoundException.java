package br.com.luizgcl.passin.domain.event.exceptions;

public class EventNotFoundException extends RuntimeException {

    public EventNotFoundException(String id) {
        super("Event not found with ID: " + id);
    }

}
