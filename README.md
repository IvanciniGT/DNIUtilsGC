# Proyecto de Validación de DNI

Este proyecto es una librería Java para validar y formatear números de DNI (Documento Nacional de Identidad) españoles.

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

En función al patrón que se cumpla, se asocia al DNI un nivel de confianza. Por ejemplo:  
- Para un DNI que no traiga letra, se asume que es un DNI válido, pero con un nivel de confianza bajo.
- Si trae los puntos en malas posiciones, se asume que es un DNI válido, pero con un nivel de confianza bajo.

## Requisitos Previos

- Java 17 o superior

## Uso

### Importación en maven:
```xml
<dependency>
    <groupId>es.atgti.datavinci</groupId>
    <artifactId>dni</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

### Uso en código:

Toda la funcionalidad se maneja desde la clase `DNI`.

```java
import es.atgti.datavinci.dni.DNIUtils;

// Crear una instancia de DNI
DNI miDNI = DNIUtils.of("23000000T");
System.out.println("Original: " + miDNI.getSource());
System.out.println("DNI: " + miDNI.getNumber());
System.out.println("Letra: " + miDNI.getControlDigit());
System.out.println("Estado: " + miDNI.getValidationInfo());
System.out.println("Puntuación: " + miDNI.getScore());
System.out.println("Válido: " + miDNI.getValidationInfo().isValid());

// Formatear un DNI
Optional<String> dniFormateado = miDNI.format();

DNIFormat dniFormat = DNIFormat.builder()
    .dots(true)
    .upperCase(true)
    .zerosPadding(true)
    .separator("-")
    .build();

Optional<String> otroDniFormateado = miDNI.format(dniFormat);
```

## Desarrollo

### Estructura del Proyecto

El proyecto tiene la siguiente estructura de directorios y archivos:
- `src/main/java/es/atgti/datavinci/DNI.java`: Clase que representa un número de DNI y su información de validación. También permite crear instancias de DNIs.
- `src/main/java/es/atgti/datavinci/DNIValidationInfo.java`: Enumeración que define los posibles estados de validación de un DNI.
- `src/main/java/es/atgti/datavinci/DNIUtils.java`: Clase de utilidad para validar números de DNI y dígitos de control y para formatear DNIs.
- `src/main/java/es/atgti/datavinci/DNIFormat.java`: Clase con patrón builder para definir el formato de un DNI.

### Autor

- [Iván Osuna Ayuste](ivan.osuna.ayuste@gmail.com)