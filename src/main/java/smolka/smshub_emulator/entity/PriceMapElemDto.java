package smolka.smshub_emulator.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class PriceMapElemDto {
    private Map<String, Integer> priceMap;
    private BigDecimal maxPrice;
    @Builder.Default
    private Boolean defaultMaxPrice = false;
    @Builder.Default
    private Boolean random = true;
    private Integer quantityForMaxPrice;
    private Integer totalQuantity;
    @Builder.Default
    private Boolean work = true;
}
