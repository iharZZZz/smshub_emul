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
    private Map<Country, Map<ServiceOfNumber, Map<BigDecimal, Integer>>> map = new HashMap<>();

    public void addElem(Country country, ServiceOfNumber service, BigDecimal cost, Integer count) {
        map.computeIfAbsent(country, c -> new HashMap<>());
        map.get(country).computeIfAbsent(service, s -> new HashMap<>());
        map.get(country).get(service).put(cost, count);
    }

    public void addServiceMap(Country country, Map<ServiceOfNumber, Map<BigDecimal, Integer>> serviceMap) {
        map.computeIfAbsent(country, c -> new HashMap<>());
        map.put(country, serviceMap);
    }

    public Map<String, Map<String, Map<String, Integer>>> getMapForResponse() {
        Map<String, Map<String, Map<String, Integer>>> result = new HashMap<>();
        for (Country country : map.keySet()) {
            result.computeIfAbsent(country.getVal(), c -> new HashMap<>());
            for (ServiceOfNumber service : map.get(country).keySet()) {
                result.get(country.getVal()).computeIfAbsent(service.getName(), s -> new HashMap<>());
                for (BigDecimal cost : map.get(country).get(service).keySet()) {
                    result.get(country.getVal()).get(service.getName()).computeIfAbsent(cost.toString(), c -> 0);
                    result.get(country.getVal()).get(service.getName()).put(cost.toString(), map.get(country).get(service).get(cost));
                }
            }
        }
        return result;
    }

    public void sub(Country country, ServiceOfNumber service, BigDecimal cost, int s) {
        map.get(country).get(service).put(cost, map.get(country).get(service).get(cost) - s);
    }
}
