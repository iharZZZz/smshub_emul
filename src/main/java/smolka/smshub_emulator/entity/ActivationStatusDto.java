package smolka.smshub_emulator.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActivationStatusDto {
    private String status;
    private List<Activation> array;
    private String msg;
}
