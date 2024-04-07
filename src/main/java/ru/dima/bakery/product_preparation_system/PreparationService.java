package ru.dima.bakery.product_preparation_system;

import org.springframework.stereotype.Service;
import ru.dima.bakery.product_preparation_system.model.Product;
import ru.dima.bakery.raw_material_purchase_system.RawRepository;
import ru.dima.bakery.raw_material_purchase_system.RawType;
import ru.dima.bakery.raw_material_purchase_system.model.Raw;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class PreparationService {
    private final ProductRepository productRepository;
    private final ProductProperties productProperties;
    private final RawRepository rawRepository;
    private final Map<ProductType, Map<RawType, Integer>> productRecipes = initializeProductRecipes();

    public PreparationService(ProductRepository productRepository, ProductProperties productProperties, RawRepository rawRepository) {
        this.productRepository = productRepository;
        this.productProperties = productProperties;
        this.rawRepository = rawRepository;
    }

    public void makeProduct(ProductType productType) throws InterruptedException {
        if (!checkWarehouse(productType)) {
            System.err.println("Не хватает сырья для приготовления продута - " + productType.name());
            return;
        }
        System.out.println("Началась готовка продукта - " + productType.name());
        saveProduct(productType);
        int time = productProperties.getProductTime().get(productType.name());
        Thread.sleep(time * 1000L);
        System.out.println("Готовка продукта " + productType.name() + " закончилась");
    }

    private void saveProduct(ProductType productType) {
        Optional<Product> existingProduct = productRepository.findProductByProductType(productType);
        Product product;
        if (existingProduct.isPresent()) {
            product = existingProduct.get();
        } else {
            product = new Product();
            product.setProductType(productType);
        }
        product.setCount(product.getCount() + 1);
        productRepository.save(product);

        useRaws(productType);
    }


    /**
     * Проверяет склад, есть ли в нем необходимое количество сырья для приготовления входного типа продукта
     *
     * @param productType входной тип продукта
     * @return true - сырья достаточно, false - сырья не хватает
     */
    private boolean checkWarehouse(ProductType productType) {
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
    private void useRaws(ProductType productType) {
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

    private Map<ProductType, Map<RawType, Integer>> initializeProductRecipes() {
        Map<ProductType, Map<RawType, Integer>> map = new HashMap<>();
        map.put(ProductType.MEAT_PIE, Map.of(RawType.DOUGH, 1, RawType.MEAT, 1));
        map.put(ProductType.CABBAGE_PIE, Map.of(RawType.DOUGH, 1, RawType.CABBAGE, 1));
        map.put(ProductType.FISH_PIE, Map.of(RawType.DOUGH, 2, RawType.FISH, 1));
        map.put(ProductType.BERRY_PIE, Map.of(RawType.DOUGH, 2, RawType.BERRIES, 2));
        map.put(ProductType.TEA, Map.of(RawType.LEAF_TEA, 1));
        map.put(ProductType.COFFEE, Map.of(RawType.COFFEE_BEANS, 2));
        return map;
    }
}
