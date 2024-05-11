package ru.dima.bakery.raw_material_purchase_system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.dima.bakery.raw_material_purchase_system.model.Raw;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class PurchaseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseService.class);
    private final RawRepository rawRepository;
    private final RawProperties rawProperties;

    public PurchaseService(RawRepository rawRepository, RawProperties rawProperties) {
        this.rawRepository = rawRepository;
        this.rawProperties = rawProperties;
    }

    public void purchaseRaw(RawType rawType, int count) throws InterruptedException {
        LOGGER.info("Покупаем - {}, количество {}", rawType.name().toLowerCase(), count);
        saveRaw(rawType, count);
        int time = rawProperties.getRawMaterials().get(rawType.name());
        TimeUnit.SECONDS.sleep(time);
        LOGGER.info("Покупка завершена");
    }

    public ResponseEntity<List<Raw>> getAllRaws() {
        List<Raw> raws = rawRepository.findAll();
        if (raws.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(raws, HttpStatus.FOUND);
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
