package ru.dima.bakery.order_system.model;

import java.util.List;
import java.util.Random;

public enum OrderType {
    BY_CAR,
    BY_BICYCLE,
    BY_FOOT;

    private static final List<OrderType> VALUES = List.of(values());
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static OrderType randomOrderType() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
