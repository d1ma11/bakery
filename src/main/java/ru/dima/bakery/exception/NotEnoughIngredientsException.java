package ru.dima.bakery.exception;

public class NotEnoughIngredientsException extends Exception {
    public NotEnoughIngredientsException(String message) {
        super(message);
    }
}
