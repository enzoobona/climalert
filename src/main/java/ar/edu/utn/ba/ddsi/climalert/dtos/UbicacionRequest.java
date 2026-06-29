package ar.edu.utn.ba.ddsi.climalert.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UbicacionRequest (
    @JsonProperty("name") String nombre
) {

}
