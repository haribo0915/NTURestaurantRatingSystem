package org.river.exceptions;


public class QueryException extends sqlException {
    public QueryException(String errMessage) {
        super(errMessage);
    }
}
