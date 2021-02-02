package smolka.smshub_emulator.entity;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Activation {
    private Integer id;
    private Integer activationId;
    private String phone;
    @Builder.Default
    private Integer status = 2;
    private LocalDateTime createDate;
    private LocalDateTime receiveSmsDate;
    private String code;
}
