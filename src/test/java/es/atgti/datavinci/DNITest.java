package es.atgti.datavinci;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DNITest {

    // Vamos a aplicar TDD:
    // Consiste en ir definiendo pruebas... Sin dar saltos...
    // Empezando por la prueba MAS TONTA POSIBLE
    // Después haremos que la prueba se cumpla...
    // Y a por el siguente paso... Una prueba un poquito (lo mínimo posible)
    // más compleja...
    @Test
    @DisplayName("Debería poder crear un objeto DNI desde un texto")
    void poderCrearUnObjetoDNIDesdeUnaCadenaDeTextoTest(){
        String texto = "23000000T";
        DNI miDNI = DNIUtils.of(texto);
        Assertions.assertNotNull(miDNI);
        Assertions.assertEquals(texto, miDNI.getSource());
        Assertions.assertEquals(23000000, miDNI.getNumber());
    }
}
