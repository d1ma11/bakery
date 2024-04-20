package ru.dima.bakery.product_preparation_system.model;

public enum OvenType {
    BIG(10),
    MEDIUM(5),
    SMALL(2);

    private final int capacity;

    OvenType(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }
}
