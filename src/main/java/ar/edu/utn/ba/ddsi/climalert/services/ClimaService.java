package ar.edu.utn.ba.ddsi.climalert.services;

import ar.edu.utn.ba.ddsi.climalert.domain.Clima;

public interface ClimaService {
    Clima obtenerClima();

    void analizarClimas();
}
