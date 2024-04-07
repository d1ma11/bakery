package ru.dima.bakery.raw_material_purchase_system;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dima.bakery.raw_material_purchase_system.model.Raw;

import java.util.Optional;

public interface RawRepository extends JpaRepository<Raw, Integer> {
    boolean existsByRawTypeAndCountGreaterThanEqual(RawType rawType, int count);
    Optional<Raw> findRawByRawType(RawType rawType);
}
