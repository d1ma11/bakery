package ru.dima.bakery.exception;

public class AllCheckoutsAreBusyException extends RuntimeException {

    public AllCheckoutsAreBusyException(String message) {
        super(message);
    }
}
