package es.atgti.datavinci;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@NoArgsConstructor (access = AccessLevel.PRIVATE)
final class DNIPatterns {

    // Patron de DNI con letra de control
        // 8 dígitos sin puntos + espacio o guión + letra de control: 12345678
    public static final String DNI_NO_DOTS_NUMERIC_PATTERN = "\\d{1,8}";
        // 1 a 2 dígitos + 3 dígitos + punto + 3 dígitos + punto + letra de control: 12.345.678
    public static final String DNI_MILLIONS_DOTTED_NUMERIC_PATTERN = "\\d{1,2}([.]\\d{3}){2}";
        // 1 a 3 dígitos + punto + 3 dígitos + letra de control: 14.567
    public static final String DNI_THOUSANDS_DOTTED_NUMERIC_PATTERN = "\\d{1,3}[.]\\d{3}";

    // Patrón de DNI con letra de control. Admite espacios y guiones entre los dígitos
    // 8 dígitos sin puntos + espacio o guión o nada + letra de control
    // Ejemplos:
    // - 12345678Z
    // - 12.345.678-Z
    // - 678 z

    public static final Pattern DNI_STRICT_PATTERN_PRESENT_CONTROL_DIGIT = Pattern.compile(
            "(("+DNI_NO_DOTS_NUMERIC_PATTERN+")|("
                    +DNI_MILLIONS_DOTTED_NUMERIC_PATTERN+")|("+
                    DNI_THOUSANDS_DOTTED_NUMERIC_PATTERN+"))" +
                    "[ -]?[a-zA-Z]");

    // Patrón de DNI sin letra de control.
    // Ejemplos:
    // - 12345678
    // - 12.345.678
    // - 14.567
    public static final Pattern DNI_STRICT_PATTERN_NO_CONTROL_DIGIT = Pattern.compile(
            "("+DNI_NO_DOTS_NUMERIC_PATTERN+")|("
                    +DNI_MILLIONS_DOTTED_NUMERIC_PATTERN+")|("+
                    DNI_THOUSANDS_DOTTED_NUMERIC_PATTERN+")");

    // Patrón de DNI sin letra de control. Admite espacios y guiones entre los dígitos
    // 8 dígitos sin puntos + espacio o guión o nada + letra de control
    // Ejemplos:
    // - 12345678Z
    // - 12345678-Z
    // - 678 z
    // - 12345678¢Z
    public static final Pattern DNI_LIGHT_REGEX = Pattern.compile("(\\d{1,8})\\D?[a-zA-Z]");

    public static final String ORDERED_DNI_CONTROL_DIGITS = "TRWAGMYFPDXBNJZSQVHLCKE";

    public static final Pattern NO_DIGITS_PATTERN = Pattern.compile("\\D");

    public enum DNIMatchedPattern {
        STRICT_PATTERN,
        STRICT_PATTERN_NO_CONTROL_DIGIT,
        LIGHT_PATTERN,
        NO_MATCH
    }
}
