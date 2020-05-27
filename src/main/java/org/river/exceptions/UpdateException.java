package org.river.exceptions;

/**
 * @author - Haribo
 */
public class UpdateException extends RuntimeException {
    public UpdateException(String errMessage) {
        super(errMessage);
    }
}
