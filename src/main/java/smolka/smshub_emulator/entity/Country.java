package smolka.smshub_emulator.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Country {
    RU("0"), UA("1"), KZ("2");
    String val;

    public static Country getByVal(String name) {
        for (Country country : values()) {
            if (country.val.equals(name)) {
                return country;
            }
        }
        return null;
    }
}
