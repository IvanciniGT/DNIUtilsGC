package es.atgti.datavinci;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class DNICreationFromNumberAndStringTest {

    @ParameterizedTest
    @DisplayName("No debería poder crear un objeto DNI válido desde un número incorrecto")
    @CsvSource({
            "-1,T",
            "100000000,T",
            "1000000000,T"
    })
    void noPoderCrearUnObjetoDNIDesdeUnNumeroIncorrectoTest(int numero, String letra){
        DNI miDNI = DNI.of(numero, letra);
        assertNotNull(miDNI);
        assertEquals(numero+letra, miDNI.getSource());
        assertTrue(miDNI.getNumber().isPresent());
        assertEquals(numero, miDNI.getNumber().get());
        assertTrue(miDNI.getControlDigit().isPresent());
        assertEquals("T", miDNI.getControlDigit().get());
        assertEquals(DNIValidationInfo.NOK_INVALID_DNI_NUMBER, miDNI.getValidationInfo());
        assertEquals(DNIValidationInfo.NOK_INVALID_DNI_NUMBER.getScore(), miDNI.getScore());
        assertFalse(miDNI.isValid());
    }

    @ParameterizedTest
    @DisplayName("No debería poder crear un objeto DNI válido desde un texto con letra incorrecta")
    @CsvSource({
            "2300000,TT",
            "23000000,TT",
            "23023,%",
            "230,TTT"
    })
    void noPoderCrearUnObjetoDNIDesdeUnaCadenaDeTextoConLetraIncorrectaTest(int numero, String letra){
        String texto = numero+letra;
        DNI miDNI = DNI.of(numero, letra);
        assertNotNull(miDNI);
        assertEquals(texto, miDNI.getSource());
        assertTrue(miDNI.getNumber().isPresent());
        assertEquals(numero, miDNI.getNumber().get());
        assertTrue(miDNI.getControlDigit().isPresent());
        assertEquals(letra, miDNI.getControlDigit().get());
        assertEquals(DNIValidationInfo.NOK_INVALID_CONTROL_DIGIT, miDNI.getValidationInfo());
        assertEquals(DNIValidationInfo.NOK_INVALID_CONTROL_DIGIT.getScore(), miDNI.getScore());
    }
    @ParameterizedTest
    @DisplayName("Debería poder crear un objeto DNI desde un numero")
    @CsvSource({
            "2300000",
            "23",
            "23023",
            "230"
    })
    void okDNIsFromNumberTest(int numero){
        DNI miDNI = DNI.of(numero);
        assertNotNull(miDNI);
        assertEquals(""+numero, miDNI.getSource());
        assertTrue(miDNI.getNumber().isPresent());
        assertEquals(numero, miDNI.getNumber().get());
        assertTrue(miDNI.getControlDigit().isPresent());
        assertEquals("T", miDNI.getControlDigit().get());
        assertEquals(DNIValidationInfo.OK_NO_DIGIT_CONTROL, miDNI.getValidationInfo());
        assertEquals(DNIValidationInfo.OK_NO_DIGIT_CONTROL.getScore(), miDNI.getScore());
        assertFalse(miDNI.isValid());
    }

    @ParameterizedTest
    @DisplayName("Debería poder crear un objeto DNI desde un numero y letra")
    @CsvSource({
            "2300000,T",
            "23,t",
            "23023,T",
            "230,t"
    })
    void okDNIsFromNumberAndStringTest(int numero, String letra){
        String texto = numero+letra;
        DNI miDNI = DNI.of(numero, letra.charAt(0));
        assertNotNull(miDNI);
        assertEquals(texto, miDNI.getSource());
        assertTrue(miDNI.getNumber().isPresent());
        assertEquals(numero, miDNI.getNumber().get());
        assertTrue(miDNI.getControlDigit().isPresent());
        assertEquals(letra.toUpperCase(), miDNI.getControlDigit().get());
        assertEquals(DNIValidationInfo.OK, miDNI.getValidationInfo());
        assertEquals(DNIValidationInfo.OK.getScore(), miDNI.getScore());
    }

    @ParameterizedTest
    @DisplayName("No debería poder crear un objeto DNI válido desde un texto con letra incorrecta")
    @CsvSource({
            "2300000,S",
            "23000000,S",
            "23023,S",
            "230,S"
    })
    void noPoderCrearUnObjetoDNIDesdeUnaCadenaDeTextoConLetraIncorrectaTest(int numero, char letra){
        String texto = ""+numero+letra;
        DNI miDNI = DNI.of(texto);
        assertNotNull(miDNI);
        assertEquals(DNIValidationInfo.NOK_INVALID_CONTROL_DIGIT, miDNI.getValidationInfo());
        assertEquals(DNIValidationInfo.NOK_INVALID_CONTROL_DIGIT.getScore(), miDNI.getScore());
    }

}
