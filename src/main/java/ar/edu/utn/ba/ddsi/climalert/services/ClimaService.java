package ar.edu.utn.ba.ddsi.climalert.services;

import ar.edu.utn.ba.ddsi.climalert.dtos.Clima;

public interface ClimaService {
    Clima obtenerClima();

    void analizarClimas();
}
