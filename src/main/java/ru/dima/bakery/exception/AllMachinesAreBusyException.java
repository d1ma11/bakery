package ru.dima.bakery.exception;

public class AllMachinesAreBusyException extends RuntimeException {
    public AllMachinesAreBusyException(String errorMessage) {
        super(errorMessage);
    }
}
