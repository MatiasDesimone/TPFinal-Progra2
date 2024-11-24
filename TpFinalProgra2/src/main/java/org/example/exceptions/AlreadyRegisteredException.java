package org.example.exceptions;

public class AlreadyRegisteredException extends Exception {

    public AlreadyRegisteredException() {
        super();
    }
    public AlreadyRegisteredException(String message) {
        super(message);
    }
}
