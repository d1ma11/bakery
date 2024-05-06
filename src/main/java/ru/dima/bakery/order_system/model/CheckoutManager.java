package ru.dima.bakery.order_system.model;

import org.springframework.stereotype.Component;
import ru.dima.bakery.exception.AllCheckoutsAreBusyException;

import java.util.ArrayList;
import java.util.List;

@Component
public class CheckoutManager {
    private final List<Checkout> checkouts;

    public CheckoutManager() {
        checkouts = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            checkouts.add(new Checkout("Checkout#"+i));
        }
    }

    public synchronized Checkout getAvailableCheckout() {
        for (Checkout checkout : checkouts) {
            if (checkout.isAvailable()) {
                return checkout;
            }
        }
        throw new AllCheckoutsAreBusyException("All the checkouts are busy right now!" +
                "Wait for a while until at least one checkout is free");
    }
}
