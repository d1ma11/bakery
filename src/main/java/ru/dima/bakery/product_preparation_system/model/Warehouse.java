package ru.dima.bakery.product_preparation_system.model;

import org.springframework.stereotype.Component;
import ru.dima.bakery.product_preparation_system.ProductType;
import ru.dima.bakery.raw_material_purchase_system.RawRepository;
import ru.dima.bakery.raw_material_purchase_system.RawType;
import ru.dima.bakery.raw_material_purchase_system.model.Raw;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class Warehouse {
    private final static Map<ProductType, Map<RawType, Integer>> productRecipes = new HashMap<>();
    private final RawRepository rawRepository;

    private Warehouse(RawRepository rawRepository) {
        this.rawRepository = rawRepository;
    }

    static {
        productRecipes.put(ProductType.MEAT_PIE, Map.of(RawType.DOUGH, 1, RawType.MEAT, 1));
        productRecipes.put(ProductType.CABBAGE_PIE, Map.of(RawType.DOUGH, 1, RawType.CABBAGE, 1));
        productRecipes.put(ProductType.FISH_PIE, Map.of(RawType.DOUGH, 2, RawType.FISH, 1));
        productRecipes.put(ProductType.BERRY_PIE, Map.of(RawType.DOUGH, 2, RawType.BERRIES, 2));
        productRecipes.put(ProductType.TEA, Map.of(RawType.LEAF_TEA, 1));
        productRecipes.put(ProductType.COFFEE, Map.of(RawType.COFFEE_BEANS, 2));
    }

    /**
     * Проверяет склад, есть ли в нем необходимое количество сырья для приготовления входного типа продукта
     *
     * @param productType входной тип продукта
     * @return true - сырья достаточно, false - сырья не хватает
     */
    public boolean checkWarehouse(ProductType productType) {
        Map<RawType, Integer> requiredRawMaterials = productRecipes.get(productType);
        for (Map.Entry<RawType, Integer> entry : requiredRawMaterials.entrySet()) {
            if (!rawRepository.existsByRawTypeAndCountGreaterThanEqual(entry.getKey(), entry.getValue())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Уменьшает количество того сырья, которое используется для создания продукта
     * @param productType продукт, который должен быть приготовлен
     */
    public void useRaws(ProductType productType) {
        Map<RawType, Integer> rawUsage = productRecipes.get(productType);
        for (Map.Entry<RawType, Integer> entry : rawUsage.entrySet()) {
            Optional<Raw> rawOptional = rawRepository.findRawByRawType(entry.getKey());
            if (rawOptional.isPresent()) {
                Raw raw = rawOptional.get();
                raw.setCount(raw.getCount() - entry.getValue());
                rawRepository.save(raw);
            } else {
                System.err.println("Недостаточно ингредиентов");
            }
        }
    }
}
