package org.river.exceptions;


public class sqlException extends RuntimeException {
    public sqlException(String errMessage) {
        super(errMessage);
    }
}
