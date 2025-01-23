package es.atgti.datavinci;

public class DNIUtils {
    public static DNI of(String texto) {
        int numero = Integer.parseInt(texto.substring(0, 8));
        return new DNI(texto, numero);
    }
}
