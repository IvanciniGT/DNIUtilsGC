package es.atgti.datavinci;

import lombok.Value;

@Value
// Nos da los getters, private final y me da un constructor con todos los argumentos
public class DNI {
        String source;
        int number;
        char controlDigit;
        DNIStatus status;
        double score;
}
