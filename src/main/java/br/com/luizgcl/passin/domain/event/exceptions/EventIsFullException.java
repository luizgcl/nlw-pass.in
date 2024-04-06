package br.com.luizgcl.passin.domain.event.exceptions;

public class EventIsFullException extends RuntimeException {

    public EventIsFullException() {
        super("Event is full");
    }
}
