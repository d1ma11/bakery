package ru.dima.bakery.product_preparation_system;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PreparationController {

    private final PreparationService preparationService;

    public PreparationController(PreparationService preparationService) {
        this.preparationService = preparationService;
    }

    @PostMapping("/preparation/{productType}")
    public void makeProduct(
            @PathVariable("productType") ProductType productType
    ) throws InterruptedException {
        preparationService.makeProduct(productType);
    }
}
