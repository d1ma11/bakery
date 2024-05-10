package ru.dima.bakery.order_system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dima.bakery.order_system.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
}
