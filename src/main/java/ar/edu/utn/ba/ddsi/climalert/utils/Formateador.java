package ar.edu.utn.ba.ddsi.climalert.utils;

import ar.edu.utn.ba.ddsi.climalert.domain.ClimaActual;
import ar.edu.utn.ba.ddsi.climalert.domain.Unidad;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Formateador {

    public String camposAStrings(ClimaActual climaActual) {
        return Arrays.stream(climaActual.getClass().getDeclaredFields())
            .map(c -> campoAString(c, climaActual))
            .collect(Collectors.joining("\n"));
    }

    private String campoAString(Field campo, ClimaActual climaActual) {
        try {
            campo.setAccessible(true);

            String nombre = formatearNombre(campo.getName());
            String unidad = campo.getAnnotation(Unidad.class) == null ? "" : campo.getAnnotation(Unidad.class).value();

            return nombre + ": " + campo.get(climaActual) + unidad;

        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private String formatearNombre(String nombre) {
        String[] palabras = nombre.split("_");
        palabras[0] = Character.toUpperCase(palabras[0].charAt(0)) + palabras[0].substring(1);
        return String.join(" ", palabras);
    }

}
