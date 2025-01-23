package es.atgti.datavinci;

import lombok.NonNull;
import lombok.Value;

import java.util.Optional;

@Value
// Nos da los getters, private final y me da un constructor con todos los argumentos
public class DNI  {

        String source;                              // El texto original del DNI.
                                                    // Si pasaron número y letra de control, será el número + letra
        Integer number;                             // El número del DNI
        String controlDigit;                        // La letra de control ya validada y en mayúsculas
        DNIValidationInfo validationInfo;           // El resultado de validar el DNI

        public Optional<Integer> getNumber(){
            return Optional.ofNullable(number);
        }

        public Optional<String> getControlDigit(){
            return Optional.ofNullable(controlDigit);
        }

        // Estos métodos no rompen el pojo... solo están para facilitar el uso de la librería

        public double getScore(){
            return validationInfo.getScore();
        }

        public boolean isValid(){
            return validationInfo.isValid();
        }

        public static DNI of(int dniNumber, String dniControlDigit) {
            return DNIUtils.of(dniNumber, dniControlDigit);
        }

        public static DNI of(int dniNumber, Character dniControlDigit) {
            return DNIUtils.of(dniNumber, dniControlDigit);
        }

        public static DNI of(int dniNumber) {
            return DNIUtils.of(dniNumber, (Character)null);
        }

        public static DNI of(String dni) {
            return DNIUtils.of(dni);
        }

        public Optional<String> format(@NonNull DNIFormat formato){
            return DNIUtils.format(this, formato);
        }

        public Optional<String> format(){
            return DNIUtils.format(this);
        }

}
