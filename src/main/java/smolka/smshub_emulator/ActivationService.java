package smolka.smshub_emulator;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import smolka.smshub_emulator.entity.*;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ActivationService {

    @AllArgsConstructor
    private static class SmsUpdater implements Runnable {
        private final Integer secsDelay;
        private final Integer limit;
        private final List<Activation> generalList;

        @Override
        public void run() {
            try {
                while (true) {
                    Thread.sleep(secsDelay * 1000);
                    List<Activation> activations = generalList.stream().filter(a -> a.getStatus().equals(2)).limit(limit).collect(Collectors.toList());
                    for (Activation activation : activations) {
                        activation.setReceiveSmsDate(LocalDateTime.now());
                        activation.setCode(UUID.randomUUID().toString());
                        activation.setStatus(6);
                    }
                }
            }
            catch (InterruptedException interruptedExc) {
                return;
            }
        }
    }

    private List<Activation> activations = new ArrayList<>();
    private PriceMap priceMap;
    private Thread smsUpdater;
    @Value(value = "${emulator.count}")
    private Integer countOfNumbers;
    @Value(value = "${emulator.price}")
    private BigDecimal price;
    @Value(value = "${emulator.updater.pause_seconds}")
    private Integer pauseSecsForUpdater;
    @Value(value = "${emulator.updater.count_for_update_step}")
    private Integer updateStepCount;

    private PriceMap createPriceMap() {
        PriceMap priceMap = new PriceMap();
        for (Country country : Country.values()) {
            for (ServiceOfNumber service : ServiceOfNumber.values()) {
                priceMap.addElem(country, service, price, countOfNumbers);
            }
        }
        return priceMap;
    }

    private Thread createSmsUpdater() {
        SmsUpdater smsUpdater = new SmsUpdater(pauseSecsForUpdater, updateStepCount, activations);
        return new Thread(smsUpdater);
    }

    @PostConstruct
    public void init() {
        priceMap = createPriceMap();
        smsUpdater = createSmsUpdater();
        smsUpdater.start();
    }

    public Object attemptToOrder(Country country, ServiceOfNumber service) {
        if (isExists(country, service, price)) {
            Activation activation = createActivation(country, service, price);
            return ActivationInfo.builder()
                    .id(activation.getId())
                    .number(activation.getPhone())
                    .build()
                    .getInfoForResponse();
        }
        return "NO_NUMBERS";
    }

    public ActivationStatusDto getActivations() {
        if (activations.isEmpty()) {
            return ActivationStatusDto.builder()
                    .msg("no_activations")
                    .build();
        }
        return ActivationStatusDto.builder()
                .status("success")
                .array(activations)
                .build();
    }

    public boolean isExists(Country country, ServiceOfNumber service, BigDecimal cost) {
        return priceMap.getMap().get(country).get(service).get(cost) > 0;
    }

    public PriceMap getPriceMap() {
        return priceMap;
    }

    private synchronized Activation createActivation(Country country, ServiceOfNumber service, BigDecimal cost) {
        int max = activations.stream().mapToInt(Activation::getId).max().orElse(-1);
        max = max + 1;
        Activation activation = Activation.builder()
                .activationId(max)
                .id(max)
                .phone(UUID.randomUUID().toString())
                .code(null)
                .status(2)
                .createDate(LocalDateTime.now())
                .build();
        priceMap.sub(country, service, cost, 1);
        activations.add(activation);
        return activation;
    }
}
