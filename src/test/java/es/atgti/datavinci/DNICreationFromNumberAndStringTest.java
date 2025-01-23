package es.atgti.datavinci;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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
        Assertions.assertNotNull(miDNI);
        Assertions.assertEquals(numero+letra, miDNI.getSource());
        Assertions.assertTrue(miDNI.getNumber().isPresent());
        Assertions.assertEquals(numero, miDNI.getNumber().get());
        Assertions.assertTrue(miDNI.getControlDigit().isPresent());
        Assertions.assertEquals("T", miDNI.getControlDigit().get());
        Assertions.assertEquals(DNIValidationInfo.NOK_INVALID_DNI_NUMBER, miDNI.getValidationInfo());
        Assertions.assertEquals(DNIValidationInfo.NOK_INVALID_DNI_NUMBER.getScore(), miDNI.getScore());
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
        Assertions.assertNotNull(miDNI);
        Assertions.assertEquals(texto, miDNI.getSource());
        Assertions.assertTrue(miDNI.getNumber().isPresent());
        Assertions.assertEquals(numero, miDNI.getNumber().get());
        Assertions.assertTrue(miDNI.getControlDigit().isPresent());
        Assertions.assertEquals(letra, miDNI.getControlDigit().get());
        Assertions.assertEquals(DNIValidationInfo.NOK_INVALID_CONTROL_DIGIT, miDNI.getValidationInfo());
        Assertions.assertEquals(DNIValidationInfo.NOK_INVALID_CONTROL_DIGIT.getScore(), miDNI.getScore());
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
        Assertions.assertNotNull(miDNI);
        Assertions.assertEquals(""+numero, miDNI.getSource());
        Assertions.assertTrue(miDNI.getNumber().isPresent());
        Assertions.assertEquals(numero, miDNI.getNumber().get());
        Assertions.assertTrue(miDNI.getControlDigit().isPresent());
        Assertions.assertEquals("T", miDNI.getControlDigit().get());
        Assertions.assertEquals(DNIValidationInfo.OK_NO_DIGIT_CONTROL, miDNI.getValidationInfo());
        Assertions.assertEquals(DNIValidationInfo.OK_NO_DIGIT_CONTROL.getScore(), miDNI.getScore());
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
        DNI miDNI = DNI.of(numero, letra);
        Assertions.assertNotNull(miDNI);
        Assertions.assertEquals(texto, miDNI.getSource());
        Assertions.assertTrue(miDNI.getNumber().isPresent());
        Assertions.assertEquals(numero, miDNI.getNumber().get());
        Assertions.assertTrue(miDNI.getControlDigit().isPresent());
        Assertions.assertEquals(letra.toUpperCase(), miDNI.getControlDigit().get());
        Assertions.assertEquals(DNIValidationInfo.OK, miDNI.getValidationInfo());
        Assertions.assertEquals(DNIValidationInfo.OK.getScore(), miDNI.getScore());
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
        Assertions.assertNotNull(miDNI);
        Assertions.assertEquals(DNIValidationInfo.NOK_INVALID_CONTROL_DIGIT, miDNI.getValidationInfo());
        Assertions.assertEquals(DNIValidationInfo.NOK_INVALID_CONTROL_DIGIT.getScore(), miDNI.getScore());
    }

}
