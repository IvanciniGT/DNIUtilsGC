package es.atgti.datavinci;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class DNICreationFromStringTest {

    @ParameterizedTest
    @DisplayName("Debería poder crear un objeto DNI desde un numero y letra")
    @CsvSource({
            "2300000,T",
            "23,t",
            "23023,T",
            "230,t"
    })
    void poderCrearUnObjetoDNIDesdeUnaCadenaDeTextoTest(int numero, String letra){
        String texto = numero+letra;
        DNI miDNI = DNIUtils.of(texto);
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
    @DisplayName("Debería poder crear un objeto DNI desde un texto con datos poco confiables")
    @CsvSource({
            "230.00.000T",
            "23...000.000T",
    })
    void poderCrearUnObjetoDNIDesdeUnaCadenaDeTextoNoConfiableTest(String dni){
        DNI miDNI = DNIUtils.of(dni);
        Assertions.assertNotNull(miDNI);
        Assertions.assertEquals(dni, miDNI.getSource());
        Assertions.assertTrue(miDNI.getNumber().isPresent());
        Assertions.assertEquals(23000000, miDNI.getNumber().get());
        Assertions.assertTrue(miDNI.getControlDigit().isPresent());
        Assertions.assertEquals("T", miDNI.getControlDigit().get());
        Assertions.assertEquals(DNIValidationInfo.OK_WEIRD_FORMAT, miDNI.getValidationInfo());
        Assertions.assertEquals(DNIValidationInfo.OK_WEIRD_FORMAT.getScore(), miDNI.getScore());

    }

    @ParameterizedTest
    @DisplayName("Debería poder crear un objeto DNI desde un texto siendo muy confiable")
    @CsvSource({
            "23.000T",
    })
    void poderCrearUnObjetoDNIDesdeUnaCadenaDeTextoConfiableTest(String dni){
        DNI miDNI = DNIUtils.of(dni);
        Assertions.assertNotNull(miDNI);
        Assertions.assertEquals(dni, miDNI.getSource());
        Assertions.assertTrue(miDNI.getNumber().isPresent());
        Assertions.assertEquals(23000, miDNI.getNumber().get());
        Assertions.assertTrue(miDNI.getControlDigit().isPresent());
        Assertions.assertEquals("T", miDNI.getControlDigit().get());
        Assertions.assertEquals(DNIValidationInfo.OK, miDNI.getValidationInfo());
        Assertions.assertEquals(DNIValidationInfo.OK.getScore(), miDNI.getScore());

    }

    // Quiero otra para DNIS que no corresponda la letra con el número
    @ParameterizedTest
    @DisplayName("No debería poder crear un objeto DNI válido desde un texto con letra incorrecta")
    @CsvSource({
            "2300000,S",
            "2.300.000,S",
    })
    void noPoderCrearUnObjetoDNIDesdeUnaCadenaDeTextoConLetraIncorrectaTest(String numero, String letra){
        String texto = numero+letra;
        DNI miDNI = DNIUtils.of(texto);
        Assertions.assertNotNull(miDNI);
        Assertions.assertEquals(texto, miDNI.getSource());
        Assertions.assertTrue(miDNI.getNumber().isPresent());
        Assertions.assertEquals(2300000, miDNI.getNumber().get());
        Assertions.assertTrue(miDNI.getControlDigit().isPresent());
        Assertions.assertEquals(letra, miDNI.getControlDigit().get());
        Assertions.assertEquals(DNIValidationInfo.NOK_INVALID_CONTROL_DIGIT, miDNI.getValidationInfo());
        Assertions.assertEquals(DNIValidationInfo.NOK_INVALID_CONTROL_DIGIT.getScore(), miDNI.getScore());
    }
    // Quiero otra para DNIS que no corresponda la letra con el número
    @ParameterizedTest
    @DisplayName("No debería poder crear un objeto DNI válido desde un texto con letra incorrecta")
    @CsvSource({
            "2300000SSS",
            "federico",
            "230S23"
    })
    void noPoderCrearUnObjetoDNIDesdeUnaCadenaDeTextoConLetraIncorrectaTest(String dni){
        DNI miDNI = DNIUtils.of(dni);
        Assertions.assertNotNull(miDNI);
        Assertions.assertEquals(DNIValidationInfo.NOK_INVALID_DNI_FORMAT, miDNI.getValidationInfo());
        Assertions.assertEquals(DNIValidationInfo.NOK_INVALID_DNI_FORMAT.getScore(), miDNI.getScore());
    }

    // Quiero otra para DNIS que no corresponda la letra con el número
    @ParameterizedTest
    @DisplayName("Debería poder crear un objeto DNI válido con poca confianza si solo viene el número")
    @CsvSource({
            "23000000",
    })
    void dniSinLetraTest(String dni){
        DNI miDNI = DNIUtils.of(dni);
        Assertions.assertNotNull(miDNI);
        Assertions.assertEquals(dni, miDNI.getSource());
        Assertions.assertTrue(miDNI.getNumber().isPresent());
        Assertions.assertEquals(23000000, miDNI.getNumber().get());
        Assertions.assertTrue(miDNI.getControlDigit().isPresent());
        Assertions.assertEquals("T", miDNI.getControlDigit().get());
        Assertions.assertEquals(DNIValidationInfo.OK_NO_DIGIT_CONTROL, miDNI.getValidationInfo());
        Assertions.assertEquals(DNIValidationInfo.OK_NO_DIGIT_CONTROL.getScore(), miDNI.getScore());
    }
    @Test
    @DisplayName("Debería poder crear un objeto DNI inválido con null")
    void dniNullTest(){
        DNI miDNI = DNIUtils.of(null);
        Assertions.assertNotNull(miDNI);
        Assertions.assertEquals(DNIValidationInfo.NOK_NULL_DNI, miDNI.getValidationInfo());
        Assertions.assertEquals(DNIValidationInfo.NOK_NULL_DNI.getScore(), miDNI.getScore());
    }

    @Test
    @DisplayName("Debería poder crear un objeto DNI inválido con cadena vacía")
    void dniEmptyTest(){
        DNI miDNI = DNIUtils.of("");
        Assertions.assertNotNull(miDNI);
        Assertions.assertEquals(DNIValidationInfo.NOK_EMPTY_DNI, miDNI.getValidationInfo());
        Assertions.assertEquals(DNIValidationInfo.NOK_EMPTY_DNI.getScore(), miDNI.getScore());
    }
}
