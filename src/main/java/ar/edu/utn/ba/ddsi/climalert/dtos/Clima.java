package ar.edu.utn.ba.ddsi.climalert.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Clima {

    private Integer id;

    @JsonProperty("location")
    private Ubicacion ubicacion;

    @JsonProperty("current")
    private ClimaActual climaActual;

}
