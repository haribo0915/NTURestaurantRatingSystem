package org.river.exceptions;


public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String errMessage) {
        super(errMessage);
    }
}