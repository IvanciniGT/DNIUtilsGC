package es.atgti.datavinci;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DNIValidationInfo {
    OK                          (1,     true),
    OK_NO_DIGIT_CONTROL         (0.5,   true),
    OK_WEIRD_FORMAT             (0.4,   true),
    NOK_INVALID_DNI_FORMAT      (0,     false),
    NOK_INVALID_CONTROL_DIGIT   (0.1,   false),
    NOK_NULL_DNI                (0,     false),
    NOK_EMPTY_DNI               (0,     false),
    NOK_INVALID_DNI_NUMBER      (0,     false);

    private final double score;
    private final boolean valid;
}
