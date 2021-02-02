package smolka.smshub_emulator.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivationInfo {
    private Integer id;
    private String number;

    public String getInfoForResponse() {
        return "ACCESS_NUMBER:"+id.toString()+":"+number;
    }
}
