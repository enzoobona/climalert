package ar.edu.utn.ba.ddsi.climalert.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClimaActual {

    @JsonProperty("temp_c")
    @Unidad("°C")
    private double temperatura;

    @JsonProperty("humidity")
    @Unidad("%")
    private int humedad;

    @JsonProperty("wind_kph")
    @Unidad("km/h")
    private double velocidad_del_viento;

    @JsonProperty("uv")
    private double indice_rayos_uv;

    @JsonProperty("precip_mm")
    @Unidad("mm")
    private double precipitacion;

    @JsonProperty("chance_of_rain")
    @Unidad("%")
    private int probabilidad_de_lluvia;

    public String camposAStrings() {
        return Arrays.stream(this.getClass().getDeclaredFields())
            .map(this::campoAString)
            .collect(Collectors.joining("\n"));
    }

    private String campoAString(Field campo) {
        try {
            campo.setAccessible(true);

            String nombre = formatearNombre(campo.getName());
            String unidad = campo.getAnnotation(Unidad.class) == null ? "" : campo.getAnnotation(Unidad.class).value();

            return nombre + ": " + campo.get(this) + unidad;

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
