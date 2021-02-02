package smolka.smshub_emulator.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceMap {
    private Map<ServiceOfNumber, PriceMapElemDto> map = new HashMap<>();

    public void addElem(ServiceOfNumber service, PriceMapElemDto priceMapElemDto) {
        map.put(service, priceMapElemDto);
    }

    public Map<String, PriceMapElemDto> getMapForResponse() {
        Map<String, PriceMapElemDto> result = new HashMap<>();
        for (ServiceOfNumber service : map.keySet()) {
            result.put(service.getName(), map.get(service));
        }
        return result;
    }

    public void sub(ServiceOfNumber service, BigDecimal cost, int s) {
        PriceMapElemDto priceMapElemDto = map.get(service);
        priceMapElemDto.getPriceMap().put(cost.toString(), priceMapElemDto.getPriceMap().get(cost.toString()) - s);
    }
}
