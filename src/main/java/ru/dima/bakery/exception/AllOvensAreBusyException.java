package ru.dima.bakery.exception;

public class AllOvensAreBusyException extends RuntimeException {
    public AllOvensAreBusyException() {
        super();
    }

    public AllOvensAreBusyException(String errorMessage) {
        super(errorMessage);
    }
}
