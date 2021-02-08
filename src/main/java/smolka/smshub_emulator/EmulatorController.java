package smolka.smshub_emulator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import smolka.smshub_emulator.entity.Country;
import smolka.smshub_emulator.entity.ServiceOfNumber;

@RestController
public class EmulatorController {

    @Autowired
    private ActivationService activationService;

    @PostMapping(value = "/emul")
    public Object entry(@RequestParam("action") String action, @RequestParam(value = "service", required = false) String service, @RequestParam(value = "country", required = false) String country) {
        if (action.equals("getPrices")) {
            return activationService.getPriceMap().getMapForResponse();
        }
        if (action.equals("getCurrentActivations")) {
            return activationService.getActivations();
        }
        if (action.equals("getNumber")) {
//            return "NO_NUMBERS";
            return activationService.attemptToOrder(Country.getByVal(country), ServiceOfNumber.getByName(service));
        }
        return null;
    }
}
