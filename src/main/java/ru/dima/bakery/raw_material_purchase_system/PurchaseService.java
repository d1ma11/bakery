package ru.dima.bakery.raw_material_purchase_system;

import org.springframework.stereotype.Service;
import ru.dima.bakery.raw_material_purchase_system.model.Raw;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class PurchaseService {

    private final RawRepository rawRepository;
    private final RawProperties rawProperties;

    public PurchaseService(RawRepository rawRepository, RawProperties rawProperties) {
        this.rawRepository = rawRepository;
        this.rawProperties = rawProperties;
    }

    public void purchaseRaw(RawType rawType, int count) throws InterruptedException {
        System.out.println("Покупаем - " + rawType.name().toLowerCase() + ", количество - " + count);
        saveRaw(rawType, count);
        int time = rawProperties.getRawMaterials().get(rawType.name());
        TimeUnit.SECONDS.sleep(time);
        System.out.println("Покупка завершена");
    }

    public List<Raw> getAllRaws() {
        return rawRepository.findAll();
    }

    public void printRawMaterials() {
        rawProperties.getRawMaterials().forEach((key, value) ->
                System.out.println("Сырье: " + key + ", количество: " + value));
    }

    private void saveRaw(RawType rawType, int count) {
        Optional<Raw> existingRaw = rawRepository.findRawByRawType(rawType);
        Raw raw;
        if (existingRaw.isPresent()) {
            raw = existingRaw.get();
            raw.setCount(raw.getCount() + count);
        } else {
            raw = new Raw();
            raw.setRawType(rawType);
            raw.setCount(count);
        }
        rawRepository.save(raw);
    }
}
