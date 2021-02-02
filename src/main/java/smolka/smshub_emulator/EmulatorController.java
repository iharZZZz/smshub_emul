package smolka.smshub_emulator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import smolka.smshub_emulator.entity.ServiceOfNumber;

@RestController
public class EmulatorController {

    @Autowired
    private ActivationService activationService;

    @PostMapping(value = "/emul")
    public Object entry(@RequestParam("action") String action, @RequestParam(value = "service", required = false) String service) {
        if (action.equals("getNumbersStatusAndCostHubFree")) {
            return activationService.getPriceMap().getMapForResponse();
        }
        if (action.equals("getCurrentActivations")) {
            return activationService.getActivations();
        }
        if (action.equals("getNumber")) {
//            return "NO_NUMBERS";
            return activationService.attemptToOrder(ServiceOfNumber.getByName(service));
        }
        return null;
    }
}
