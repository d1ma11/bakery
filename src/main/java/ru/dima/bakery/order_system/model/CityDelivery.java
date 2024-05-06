package ru.dima.bakery.order_system.model;

import java.util.List;
import java.util.Random;

public enum CityDelivery {
    MOSCOW,
    PETERSBURG,
    NOVOSIBIRSK,
    YEKATERINBURG;

    private static final List<CityDelivery> VALUES = List.of(values());
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static CityDelivery randomCityDelivery() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
