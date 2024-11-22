package org.example.exceptions;

public class InvalidFieldException extends Exception {

    public InvalidFieldException() {
        super();
    }

    public InvalidFieldException(String message) {
        super(message);
    }
}
