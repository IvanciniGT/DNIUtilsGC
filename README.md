# Librería para trabajar con DNIs

Esta librería permite trabajar con DNIs con diferentes formatos.
Se admiten cosas como:

    23000000
    23000000T
    23.000.000
    23.000.000T
    23.000.000t
    23.000.000 T
    23.000.000-T
    23000000T
    23.000T
    23.000 T
    23T

En función al patrón que se cumpla, se asocia al DNI un nivel de confianza...
Por ejemplo, para un DNI que no traiga letra, se asume que es un DNI válido, pero con un nivel de confianza bajo.
Si por ejemplo trae los puntos en malas posiciones, se asume que es un DNI válido, pero con un nivel de confianza bajo.

## Ejemplos de uso:

### Importación en maven:
```xml
<dependency>
    <groupId>es.atgti.datavinci</groupId>
    <artifactId>dni</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

### Uso en código:
```java
import es.atgti.datavinci.dni.DNIUtils;

DNI miDNI = DNIUtils.of("23000000T");
System.out.println("Original: " + miDNI.getSource());
System.out.println("DNI: " + miDNI.getNumber());
System.out.println("Letra: " + miDNI.getControlDigit());
System.out.println("Estado: " + miDNI.getStatus());
System.out.println("Puntuación: " + miDNI.getScore());
```
