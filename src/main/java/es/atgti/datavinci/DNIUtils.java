package es.atgti.datavinci;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Optional;

import static es.atgti.datavinci.DNIPatterns.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class DNIUtils {

    static Optional<String> format(DNI dni, @NonNull DNIFormat formato){
        if(!dni.getValidationInfo().isValid())
            return Optional.empty();

        String dniNumber = ""+dni.getNumber().orElseThrow();
        if (formato.isZerosPadding())
            dniNumber = ("00000000" + dniNumber).substring(dniNumber.length());

        String dniControlDigit = dni.getControlDigit().orElseThrow();
        if (formato.isDots()) {
            // Si tiene más de 3 dígitos, añadimos puntos
            if (dniNumber.length() > 3) {
                dniNumber = dniNumber.substring(0, dniNumber.length() - 3) + "." + dniNumber.substring(dniNumber.length() - 3);
            }
            // Si tiene más de 6 dígitos, añadimos puntos
            if (dniNumber.length() > 7) {
                dniNumber = dniNumber.substring(0, dniNumber.length() - 7) + "." + dniNumber.substring(dniNumber.length() - 7);
            }
        }

        dniControlDigit = formato.isUpperCase() ? dniControlDigit.toUpperCase() : dniControlDigit.toLowerCase();
        return Optional.of(dniNumber + formato.getSeparator().orElse("") + dniControlDigit);
    }
    
    static Optional<String> format(DNI dni){
        return format(dni, DNIFormat.DEFAULT);
    }

    static DNI of(int dniNumber, String dniControlDigit) {
        if (dniControlDigit == null || dniControlDigit.isEmpty()) {     // Si no viene la letra de control, la intentaremos calcular
            return of(dniNumber, (Character) null); 
        } else if (dniControlDigit.trim().length() == 1) {                     // Si viene la letra de control, validaremos el DNI
            dniControlDigit = dniControlDigit.trim();                   // Limpiamos la letra de control de espacios
            return of(dniNumber, dniControlDigit.charAt(0));
        } else {                                                        // Si viene más de una letra de control, es un error
            return new DNI(dniNumber+dniControlDigit, dniNumber, dniControlDigit, DNIValidationInfo.NOK_INVALID_CONTROL_DIGIT);
        }
    }
    
    static DNI of(int dniNumber, Character dniControlDigit) {
        DNIValidationInfo validationInfo;
        String calculatedControlDigit = dniControlDigit == null ? null : "" + dniControlDigit;
        if (dniNumber < 0 || dniNumber > 99999999) {                        // Si el número del DNI no está en el rango
            validationInfo = DNIValidationInfo.NOK_INVALID_DNI_NUMBER;
        } else if (dniControlDigit == null) {                               // Si no viene la letra de control, la intentaremos calcular
            validationInfo = DNIValidationInfo.OK_NO_DIGIT_CONTROL;
            calculatedControlDigit = calculateControlDigitForDNINumber(dniNumber).toString();
        } else{                                                             // Si viene la letra de control
            calculatedControlDigit = ""+Character.toUpperCase(dniControlDigit);
            if (isControlDigitValid(dniNumber, Character.toUpperCase(dniControlDigit))) {  // Sí está correcto
                validationInfo = DNIValidationInfo.OK;
            } else {                                                            // Sí viene la letra de control, validaremos el DNI
                validationInfo = DNIValidationInfo.NOK_INVALID_CONTROL_DIGIT;
            }
        }
        String sourceDNI = dniNumber + (dniControlDigit == null ? "" : dniControlDigit.toString());
        calculatedControlDigit = calculatedControlDigit == null ? "" : calculatedControlDigit;
        return new DNI(sourceDNI, dniNumber, calculatedControlDigit, validationInfo);
    }

    static DNI of(String dni) {
        dni = dni != null ? dni.trim() : null;                                  // Limpiamos el DNI de espacios
        DNIValidationInfo validationInfo;
        Integer dniNumber = null;
        String dniControlDigit = null;

        if(dni == null ){                                                       // Si el DNI es nulo
            validationInfo = DNIValidationInfo.NOK_NULL_DNI;
        } else if(dni.isEmpty()){                                               // Si el DNI está vacío
            validationInfo = DNIValidationInfo.NOK_EMPTY_DNI;
        } else {                                                                // Si el DNI tiene contenido
            DNIMatchedPattern matchedPattern = matchDNIPattern(dni);            // Intentamos identificar el patrón del DNI
            if (matchedPattern == DNIMatchedPattern.NO_MATCH) {                 // Si no se identifica el patrón
                validationInfo = DNIValidationInfo.NOK_INVALID_DNI_FORMAT;
            } else {                                                            // Sí se identifica el patrón
                String dniNumberString = NO_DIGITS_PATTERN.matcher(dni).replaceAll("");
                dniNumber = Integer.parseInt(dniNumberString);
                // Pasaremos los datos a la función de validación.
                // Si da OK, habrá que corregir la confianza dependiendo del patrón que se haya identificado
                DNIValidationInfo correctedValidationInfo = getCorrectedValidationInfo(matchedPattern);
                if (matchedPattern != DNIMatchedPattern.STRICT_PATTERN_NO_CONTROL_DIGIT)    // Si viene la letra de control la extraemos
                    dniControlDigit = dni.substring(dni.length()-1);
                DNI dniObject = of(dniNumber, dniControlDigit);                             // Validamos el DNI con los datos extraídos
                dniControlDigit = dniObject.getControlDigit().orElse(null);           // Actualizamos la letra de control (mayúscula)
                validationInfo = dniObject.getValidationInfo() == DNIValidationInfo.OK ? correctedValidationInfo : dniObject.getValidationInfo();
            }
        }
        return new DNI(dni, dniNumber, dniControlDigit, validationInfo);
    }

    private static DNIValidationInfo getCorrectedValidationInfo(DNIMatchedPattern matchedPattern){
        return matchedPattern == DNIMatchedPattern.LIGHT_PATTERN?
                DNIValidationInfo.OK_WEIRD_FORMAT:
                DNIValidationInfo.OK;
    }

    private static DNIMatchedPattern matchDNIPattern(String dni) {
        if (DNI_STRICT_PATTERN_PRESENT_CONTROL_DIGIT.matcher(dni).matches()) {
            return DNIMatchedPattern.STRICT_PATTERN;
        } else if (DNI_STRICT_PATTERN_NO_CONTROL_DIGIT.matcher(dni).matches()) {
            return DNIMatchedPattern.STRICT_PATTERN_NO_CONTROL_DIGIT;
        } else if (DNI_LIGHT_REGEX.matcher(dni.replace(".","")).matches()) {
            return DNIMatchedPattern.LIGHT_PATTERN;
        } else {
            return DNIMatchedPattern.NO_MATCH;
        }
    }

    private static boolean isControlDigitValid(int dniNumber, char dniControlDigit){
        return calculateControlDigitForDNINumber(dniNumber) == dniControlDigit;
    }

    private static Character calculateControlDigitForDNINumber(int dniNumber){
        return ORDERED_DNI_CONTROL_DIGITS.charAt(dniNumber % 23);
    }
}
