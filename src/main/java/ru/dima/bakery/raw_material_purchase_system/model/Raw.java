package ru.dima.bakery.raw_material_purchase_system.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import ru.dima.bakery.raw_material_purchase_system.RawType;

@Entity
public class Raw {
    @Id
    @Enumerated(EnumType.STRING)
    private RawType rawType;

    private int count;

    public Raw() {
    }

    public Raw(RawType rawType, int count) {
        this.rawType = rawType;
        this.count = count;
    }

    public RawType getRawType() {
        return rawType;
    }

    public void setRawType(RawType rawType) {
        this.rawType = rawType;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
