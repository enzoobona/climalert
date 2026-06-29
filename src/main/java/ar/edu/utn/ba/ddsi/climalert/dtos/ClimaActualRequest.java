package ar.edu.utn.ba.ddsi.climalert.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ClimaActualRequest (
    @JsonProperty("temp_c") double temperatura,
    @JsonProperty("humidity") int humedad,
    @JsonProperty("wind_kph") double velocidad_del_viento,
    @JsonProperty("uv") double indice_rayos_uv,
    @JsonProperty("precip_mm") double precipitacion,
    @JsonProperty("chance_of_rain") int probabilidad_de_lluvia
) {

}
