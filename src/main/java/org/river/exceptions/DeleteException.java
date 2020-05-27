package org.river.exceptions;

/**
 * @author - Haribo
 */
public class DeleteException extends RuntimeException {
    public DeleteException(String errMessage) {
        super(errMessage);
    }
}
