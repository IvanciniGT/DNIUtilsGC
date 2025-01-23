package es.atgti.datavinci;

public interface DNIUtils {

    String DIGITOS_CONTROL = "TRWAGMYFPDXBNJZSQVHLCKE";
    String NUMBER_PATTERN = "[\\D]";
    String DNI_SIMPLE_NUMERIC_PATTERN = "[\\d]{1,8}";
    String DNI_COMPLEX_NUMERIC_PATTERN = "([\\d]{1,2}([.][\\d]{3}){2})|([\\d]{1,3}[.][\\d]{3})";
    String DNI_REGEX = "(("+DNI_SIMPLE_NUMERIC_PATTERN+")|"+DNI_COMPLEX_NUMERIC_PATTERN+")[ -]?[a-zA-Z]";
    String DNI_REGEX_NO_CONTROL_DIGIT = "(("+DNI_SIMPLE_NUMERIC_PATTERN+")|"+DNI_COMPLEX_NUMERIC_PATTERN+")";
    String DNI_LIGHT_REGEX = "([\\d]{1,8})\\D?[a-zA-Z]";

    static String format(DNI dni, DNIFormat formato){
        // TODO: Pendiente de implementar
        return null;
    }

    static DNI of(int numero, String letra) {
        // TODO: Pendiente de implementar
        return null;
    }
    static DNI of(int numero, char letra) {
        return of(numero, ""+letra);
    }

    static DNI of(String dni) {
        DNI dniADevolver;
        boolean cumplePatronRigido = dni.matches(DNI_REGEX);
        boolean cumplePatronNoControlDigit = false;
        boolean cumplePatronLight = cumplePatronRigido;
        double score = 0;
        String dniSinPuntos = dni.replace(".","");
        if(!cumplePatronRigido){
            cumplePatronNoControlDigit = dni.matches(DNI_REGEX_NO_CONTROL_DIGIT);
            if(!cumplePatronNoControlDigit) {
                // Voy a ver si el dni que viene cumple con el formato que debería tener un DNI
                cumplePatronLight = dniSinPuntos.matches(DNI_LIGHT_REGEX);
            }
        }
        if(!cumplePatronLight && !cumplePatronNoControlDigit){ // Intentamos también con un patrón menos rígido, que no tiene tan en cuenta la posición de los puntos
            dniADevolver= new DNI(dni, 0, ' ', DNIStatus.INVALID_DNI_FORMAT, score);
        } else {
            // Extraer desde el principio lo que sean números (con regex)
            String numeros= dni.replaceAll(NUMBER_PATTERN, "");
            int numero = Integer.parseInt(numeros);
            // El numero puede tener entre 1 y 8... la letra es lo ultimo
            char letra = cumplePatronNoControlDigit?getDigitoControl(numero) : dni.charAt(dni.length()-1);
            DNIStatus status = calculateDNIStatus(cumplePatronNoControlDigit, numero, letra);
            score = calculateDNIScore(status, cumplePatronRigido);
            dniADevolver = new DNI(dni, numero, letra, status, score);
        }
        return dniADevolver;
    }

    private static double calculateDNIScore(DNIStatus status, boolean cumplePatronRigido) {
        return switch (status) {
            case OK -> cumplePatronRigido ? 1 : 0.5;
            case INVALID_CONTROL_DIGIT -> 0;
            case OK_NO_DIGIT_CONTROL -> 0.3;
            default -> 0.5;
        };
    }

    private static DNIStatus calculateDNIStatus(boolean cumplePatronNoControlDigit, int numero, char letra) {
        DNIStatus status = DNIStatus.OK_NO_DIGIT_CONTROL;
        if(!cumplePatronNoControlDigit)
            status = esDigitoControlValido(numero, letra) ? DNIStatus.OK : DNIStatus.INVALID_CONTROL_DIGIT;
        return status;
    }

    private static boolean esDigitoControlValido(int numero, char letra){
        return getDigitoControl(numero) == letra;
    }

    private static char getDigitoControl(int numero){
        return DIGITOS_CONTROL.charAt(numero % 23);
    }
}
