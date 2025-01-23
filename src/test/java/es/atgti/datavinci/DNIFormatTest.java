package es.atgti.datavinci;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Optional;

class DNIFormatTest {

        @ParameterizedTest
        @DisplayName("Debería formatear correctamente un DNI válido sin puntos y en mayúsculas")
        @CsvSource({
                "2300000,T,2300000T",
                "12345678,Z,12345678Z"
        })
        void shouldFormatValidDNIWithoutDotsAndUpperCase(int dniNumber, String dniControlDigit, String expected) {
            DNI dni = DNIUtils.of(dniNumber, dniControlDigit);
            DNIFormat format = new DNIFormat(false, true, false,"");
            Optional<String> formattedDNI = DNIUtils.format(dni, format);
            Assertions.assertTrue(formattedDNI.isPresent());
            Assertions.assertEquals(expected, formattedDNI.get());
        }

        @ParameterizedTest
        @DisplayName("Debería formatear correctamente un DNI válido con puntos y en minúsculas")
        @CsvSource({
                "2300000,T,2.300.000t",
                "12345678,Z,12.345.678z"
        })
        void shouldFormatValidDNIWithDotsAndLowerCase(int dniNumber, String dniControlDigit, String expected) {
            DNI dni = DNIUtils.of(dniNumber, dniControlDigit);
            DNIFormat format = new DNIFormat(true, false, false,"");
            Optional<String> formattedDNI = DNIUtils.format(dni, format);
            Assertions.assertTrue(formattedDNI.isPresent());
            Assertions.assertEquals(expected, formattedDNI.get());
        }

        @ParameterizedTest
        @DisplayName("Debería devolver vacío para un DNI inválido")
        @CsvSource({
                "-1,T",
                "100000000,T",
                "2300000,TT"
        })
        void shouldReturnEmptyForInvalidDNI(int dniNumber, String dniControlDigit) {
            DNI dni = DNIUtils.of(dniNumber, dniControlDigit);
            DNIFormat format = new DNIFormat(false, true, false,"");
            Optional<String> formattedDNI = DNIUtils.format(dni, format);
            Assertions.assertFalse(formattedDNI.isPresent());
        }

        @ParameterizedTest
        @DisplayName("Debería formatear correctamente un DNI válido con separador personalizado")
        @CsvSource({
                "2300000,T,2300000-T",
                "12345678,Z,12345678-Z"
        })
        void shouldFormatValidDNIWithCustomSeparator(int dniNumber, String dniControlDigit, String expected) {
            DNI dni = DNIUtils.of(dniNumber, dniControlDigit);
            DNIFormat format = new DNIFormat(false, true, false,"-");
            Optional<String> formattedDNI = DNIUtils.format(dni, format);
            Assertions.assertTrue(formattedDNI.isPresent());
            Assertions.assertEquals(expected, formattedDNI.get());
        }

        @ParameterizedTest
        @DisplayName("Debería formatear correctamente un DNI válido con ceros a la izquierda")
        @CsvSource({
                "2300000,T,02300000T",
                "23000,t,00023000T"
        })
        void shouldFormatValidDNIWithLeadingZeros(int dniNumber, String dniControlDigit, String expected) {
            DNI dni = DNI.of(dniNumber, dniControlDigit);
            DNIFormat format = new DNIFormat(false, true, true,"");
            Optional<String> formattedDNI = dni.format(format);
            Assertions.assertTrue(formattedDNI.isPresent());
            Assertions.assertEquals(expected, formattedDNI.get());
        }

    @ParameterizedTest
    @DisplayName("Debería formatear correctamente un DNI válido con ceros a la izquierda y separador personalizado y puntos")
    @CsvSource({
            "2300000,T,02.300.000-t",
            "23000,t,00.023.000-t"
    })
    void shouldFormatValidDNIWithLeadingZerosAndCustomSeparatorAndDots(int dniNumber, String dniControlDigit, String expected) {
        DNI dni = DNI.of(dniNumber, dniControlDigit);
        DNIFormat format = DNIFormat.builder()
                .dots(true)
                .upperCase(false)
                .zerosPadding(true)
                .separator("-")
                .build();
        Optional<String> formattedDNI = dni.format(format);
        Assertions.assertTrue(formattedDNI.isPresent());
        Assertions.assertEquals(expected, formattedDNI.get());
    }

    @ParameterizedTest
    @DisplayName("Debería formatear correctamente un DNI válido el formato por defecto")
    @CsvSource({
            "2300000,T,2300000T",
            "23000,t,23000T"
    })
    void shouldFormatValidDNIWithDefaultFormat(int dniNumber, String dniControlDigit, String expected) {
        DNI dni = DNI.of(dniNumber, dniControlDigit);
        Optional<String> formattedDNI = dni.format();
        Assertions.assertTrue(formattedDNI.isPresent());
        Assertions.assertEquals(expected, formattedDNI.get());
    }

}