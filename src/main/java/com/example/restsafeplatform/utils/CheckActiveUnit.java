package com.example.restsafeplatform.utils;

import com.example.restsafeplatform.admin.dto.entity.Unit;
import com.example.restsafeplatform.admin.repository.UnitRepository;
import com.example.restsafeplatform.common.exception.UserNonExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Slf4j
@Component
public class CheckActiveUnit {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private final UnitRepository unitRepository;
    private final ServiceConfiguration serviceConfiguration;

    @Autowired
    public CheckActiveUnit(UnitRepository unitRepository, ServiceConfiguration serviceConfiguration) {
        this.unitRepository = unitRepository;
        this.serviceConfiguration = serviceConfiguration;
    }

    public boolean taskThread(String serial) throws ExecutionException, InterruptedException {
        log.info("Вызов нового тред пула");
        Future<Boolean> foo = serviceConfiguration.getExecutorService().submit(() -> checkUnit(serial));
        return foo.get();
    }

    public boolean checkUnit(String serial) throws UserNonExistException {
        int count = 0;
        int numberOfTry = 60;
        while (count < numberOfTry) {
            log.info("The time is now {}", dateFormat.format(new Date()));
            Optional<Unit> unit = unitRepository.getUnitBySerial(serial);
            if (unit.isPresent()) {
                if (unit.get().isActive()) {
                    return true;
                } else {
                    sleepTread();
                    count += 1;
                }
            }
        }
        throw new UserNonExistException("asd");
    }

    private void sleepTread() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Interrupted exception: message=[{}]", e.toString());
        }
    }
}
