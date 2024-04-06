package br.com.luizgcl.passin.domain.checkin.exceptions;

public class CheckInAlreadyExistsException extends RuntimeException {

    public CheckInAlreadyExistsException() {
        super("CheckIn already exists");
    }
}
