package ru.dima.bakery.product_preparation_system.model;

import org.springframework.stereotype.Component;
import ru.dima.bakery.exception.AllMachinesAreBusyException;
import ru.dima.bakery.exception.AllOvensAreBusyException;
import ru.dima.bakery.product_preparation_system.ProductType;

import java.util.ArrayList;
import java.util.List;

@Component
public class CookingManager {
    private final List<Oven> ovens;
    private final List<Machine> machines;

    public CookingManager() {
        ovens = new ArrayList<>();
        machines = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            ovens.add(new Oven(OvenType.BIG, OvenType.BIG.getCapacity()));
        }
        for (int i = 0; i < 3; i++) {
            ovens.add(new Oven(OvenType.MEDIUM, OvenType.MEDIUM.getCapacity()));
        }
        for (int i = 0; i < 5; i++) {
            ovens.add(new Oven(OvenType.SMALL, OvenType.SMALL.getCapacity()));
        }
        machines.add(new TeaMachine());
        machines.add(new CoffeeMachine());
    }

    public synchronized Oven getAvailableOven() {
        for (Oven oven : ovens) {
            if (oven.getCurrentLoad().get() < oven.getCapacity()) {
                return oven;
            }
        }
        throw new AllOvensAreBusyException("All the ovens are busy right now!" +
                " Wait for a while until at least one oven is free");
    }

    public synchronized Machine getAvailableMachine(ProductType productType) {
        for (Machine machine : machines) {
            if (machine.isAvailable() && machine.getProductType() == productType) {
                return machine;
            }
        }
        throw new AllMachinesAreBusyException("All the machines are busy right now!" +
                " Wait for a while until at least one machine is free");
    }
}
