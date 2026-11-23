package enums;

import java.util.Locale;

public enum LanguageEnum {
    ENGLISH,
    PORTUGUESE;

    public static LanguageEnum from() { return ENGLISH; }
    public static LanguageEnum from(String mode) {
        try {
            return valueOf(mode.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException(
                "Language must be english or portuguese: " + mode,
                exception
            );
        }
    }
}
