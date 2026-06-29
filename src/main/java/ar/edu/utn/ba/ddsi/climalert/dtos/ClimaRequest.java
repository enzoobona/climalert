package ar.edu.utn.ba.ddsi.climalert.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ClimaRequest(
    @JsonProperty("location") UbicacionRequest ubicacionRequest,
    @JsonProperty("current") ClimaActualRequest climaActualRequest
) {

}
