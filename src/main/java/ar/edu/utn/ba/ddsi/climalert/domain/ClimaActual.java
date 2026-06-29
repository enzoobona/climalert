package ar.edu.utn.ba.ddsi.climalert.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClimaActual {

    @Unidad("°C")
    private double temperatura;

    @Unidad("%")
    private int humedad;

    @Unidad("km/h")
    private double velocidad_del_viento;

    private double indice_rayos_uv;

    @Unidad("mm")
    private double precipitacion;

    @Unidad("%")
    private int probabilidad_de_lluvia;



    public boolean condicionClimaticaPeligrosa() {
        return this.temperatura > 35.0 && this.humedad > 60;
    }

}
