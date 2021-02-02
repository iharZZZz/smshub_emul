package smolka.smshub_emulator.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ServiceOfNumber {
    VK("vk"), OK("ok"), TELEGRAM("tg");
    String name;

    public static ServiceOfNumber getByName(String name) {
        for (ServiceOfNumber serviceOfNumber : values()) {
            if (serviceOfNumber.name.equals(name)) {
                return serviceOfNumber;
            }
        }
        return null;
    }
}
