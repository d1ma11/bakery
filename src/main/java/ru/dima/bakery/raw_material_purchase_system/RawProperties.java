package ru.dima.bakery.raw_material_purchase_system;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RawProperties {
    @Value("#{${user.raw-time}}")
    private Map<String, Integer> rawMaterials;

    public Map<String, Integer> getRawMaterials() {
        return rawMaterials;
    }

    public void setRawMaterials(Map<String, Integer> rawMaterials) {
        this.rawMaterials = rawMaterials;
    }
}
