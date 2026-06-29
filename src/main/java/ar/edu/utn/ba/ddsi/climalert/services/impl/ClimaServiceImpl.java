package ar.edu.utn.ba.ddsi.climalert.services.impl;

import ar.edu.utn.ba.ddsi.climalert.config.RestWeatherProperties;
import ar.edu.utn.ba.ddsi.climalert.domain.Clima;
import ar.edu.utn.ba.ddsi.climalert.domain.ClimaActual;
import ar.edu.utn.ba.ddsi.climalert.domain.Ubicacion;
import ar.edu.utn.ba.ddsi.climalert.dtos.ClimaActualRequest;
import ar.edu.utn.ba.ddsi.climalert.dtos.ClimaRequest;
import ar.edu.utn.ba.ddsi.climalert.dtos.UbicacionRequest;
import ar.edu.utn.ba.ddsi.climalert.repositories.ClimaRepository;
import ar.edu.utn.ba.ddsi.climalert.services.AlertaService;
import ar.edu.utn.ba.ddsi.climalert.services.ClimaService;
import ar.edu.utn.ba.ddsi.climalert.utils.Formateador;
import java.net.URI;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class ClimaServiceImpl implements ClimaService {

    private RestTemplate restTemplate;
    private RestWeatherProperties properties;
    private ClimaRepository climaRepository;
    private AlertaService alertaService;
    private Formateador formateador;

    public ClimaServiceImpl(RestTemplate restTemplate, RestWeatherProperties properties, ClimaRepository climaRepository, AlertaService alertaService) {
        this.restTemplate = restTemplate;
        this.properties = properties;
        this.climaRepository = climaRepository;
        this.alertaService = alertaService;
    }

    @Override
    public Clima obtenerClima() {
        URI uri = UriComponentsBuilder
            .fromUriString(properties.getBaseUrl())
            .path(properties.getMethod())
            .queryParam("key", properties.getKey())
            .queryParam("q", properties.getCityName())
            .build()
            .toUri();

        ClimaRequest climaRequest = restTemplate.getForObject(uri, ClimaRequest.class);

        if(climaRequest == null) return null;
        Clima clima = climaRequestToDomain(climaRequest);

        this.climaRepository.save(clima);

        return clima;
    }

    private Clima climaRequestToDomain(ClimaRequest request) {
        return new Clima(
            0L,
            ubicacionRequestToDomain(request.ubicacionRequest()),
            climaActualRequestToDomain(request.climaActualRequest())
        );
    }

    private Ubicacion ubicacionRequestToDomain(UbicacionRequest request) {
        return new Ubicacion(
            request.nombre()
        );
    }

    private ClimaActual climaActualRequestToDomain(ClimaActualRequest request) {
        return new ClimaActual(
            request.temperatura(),
            request.humedad(),
            request.velocidad_del_viento(),
            request.indice_rayos_uv(),
            request.precipitacion(),
            request.probabilidad_de_lluvia()
        );
    }

    @Override
    public void analizarClimas() {

        Optional<Clima> ultimo = this.climaRepository.findLast();
        Clima clima = ultimo.get();

        if(!clima.getClimaActual().condicionClimaticaPeligrosa()) return;

        String alerta = "Clima en " + clima.getUbicacion().getNombre() + "\n" +  formateador.camposAStrings(clima.getClimaActual());

        this.alertaService.enviarAlerta(alerta);

    }



}
