package es.atgti.datavinci;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class DNITest {

    // Vamos a aplicar TDD:
    // Consiste en ir definiendo pruebas... Sin dar saltos...
    // Empezando por la prueba MAS TONTA POSIBLE
    // Después haremos que la prueba se cumpla...
    // Y a por el siguente paso... Una prueba un poquito (lo mínimo posible)
    // más compleja...
    @ParameterizedTest
    @DisplayName("Debería poder crear un objeto DNI desde un numero y letra")
    @CsvSource({
            "2300000,T",
            "23,T",
            "23023,T",
            "230,T"
    })
    void poderCrearUnObjetoDNIDesdeUnaCadenaDeTextoTest(int numero, char letra){
        String texto = ""+numero+letra;
        DNI miDNI = DNIUtils.of(texto);
        Assertions.assertNotNull(miDNI);
        Assertions.assertEquals(texto, miDNI.getSource());
        Assertions.assertEquals(numero, miDNI.getNumber());
        Assertions.assertEquals(letra, miDNI.getControlDigit());
        Assertions.assertEquals(DNIStatus.OK, miDNI.getStatus());
        Assertions.assertEquals(1, miDNI.getScore());
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
        Assertions.assertTrue(miDNI.getNumber()%23==0);
        Assertions.assertEquals('T', miDNI.getControlDigit());
        Assertions.assertEquals(DNIStatus.OK, miDNI.getStatus());
        Assertions.assertEquals(0.5, miDNI.getScore());

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
        Assertions.assertTrue(miDNI.getNumber()%23==0);
        Assertions.assertEquals('T', miDNI.getControlDigit());
        Assertions.assertEquals(DNIStatus.OK, miDNI.getStatus());
        Assertions.assertEquals(1, miDNI.getScore());

    }

    // Quiero otra para DNIS que no corresponda la letra con el número
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
        DNI miDNI = DNIUtils.of(texto);
        Assertions.assertNotNull(miDNI);
        Assertions.assertEquals(DNIStatus.INVALID_CONTROL_DIGIT, miDNI.getStatus());
        Assertions.assertEquals(0, miDNI.getScore());
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
        Assertions.assertEquals(DNIStatus.INVALID_DNI_FORMAT, miDNI.getStatus());
        Assertions.assertEquals(0, miDNI.getScore());
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
        Assertions.assertEquals(DNIStatus.OK_NO_DIGIT_CONTROL, miDNI.getStatus());
        Assertions.assertEquals(0.3, miDNI.getScore());
    }
}
