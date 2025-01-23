package es.atgti.datavinci;

import lombok.Builder;
import lombok.Value;

import java.util.Optional;

@Value
@Builder
public class DNIFormat {

    @Builder.Default
    boolean dots = false;               // Sí se deben incluir puntos en el número del DNI
    @Builder.Default
    boolean upperCase = true;           // Sí la letra de control debe ir en mayúsculas
    @Builder.Default
    boolean zerosPadding = false;       // Sí se deben rellenar con ceros los números del DNI de menos de 8 dígitos
    @Builder.Default
    String separator = null;            // Separador personalizado

    public Optional<String> getSeparator(){
        return Optional.ofNullable(separator);
    }

    public static final DNIFormat DEFAULT = DNIFormat.builder().build();
}
