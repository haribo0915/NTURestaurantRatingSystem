package org.river.exceptions;


public class resourceNotFoundException extends RuntimeException {
    public resourceNotFoundException(String errMessage) {
        super(errMessage);
    }
}
