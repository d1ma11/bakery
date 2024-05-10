package ru.dima.bakery.product_preparation_system.model;

import org.springframework.stereotype.Component;
import ru.dima.bakery.exception.NotEnoughRawException;
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
     */
    public void checkWarehouse(ProductType productType) throws NotEnoughRawException {
        Map<RawType, Integer> requiredRawMaterials = productRecipes.get(productType);

        for (Map.Entry<RawType, Integer> entry : requiredRawMaterials.entrySet()) {
            if (!rawRepository.existsByRawTypeAndCountGreaterThanEqual(entry.getKey(), entry.getValue())) {
                throw new NotEnoughRawException("Не хватает сырья для приготовления продута - " + productType.name());
            }
        }
    }

    /**
     * Уменьшает количество того сырья, которое используется для создания продукта
     *
     * @param productType продукт, который должен быть приготовлен
     */
    public void useRaws(ProductType productType) {
        Map<RawType, Integer> rawUsageForProduct = productRecipes.get(productType);

        for (Map.Entry<RawType, Integer> rawTypeAndHisQuantity : rawUsageForProduct.entrySet()) {
            RawType recipeRaw = rawTypeAndHisQuantity.getKey();
            Optional<Raw> rawOptional = rawRepository.findRawByRawType(recipeRaw);

            if (rawOptional.isPresent()) {
                Raw raw = rawOptional.get();
                raw.setCount(raw.getCount() - rawTypeAndHisQuantity.getValue());

                rawRepository.save(raw);
            }
        }
    }
}
