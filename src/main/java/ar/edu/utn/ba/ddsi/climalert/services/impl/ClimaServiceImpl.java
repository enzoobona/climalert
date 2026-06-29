package ar.edu.utn.ba.ddsi.climalert.services.impl;

import ar.edu.utn.ba.ddsi.climalert.config.RestWeatherProperties;
import ar.edu.utn.ba.ddsi.climalert.dtos.Clima;
import ar.edu.utn.ba.ddsi.climalert.repositories.ClimaRepository;
import ar.edu.utn.ba.ddsi.climalert.services.AlertaService;
import ar.edu.utn.ba.ddsi.climalert.services.ClimaService;
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

        Clima clima = restTemplate.getForObject(uri, Clima.class);

        this.climaRepository.save(clima);

        return clima;
    }

    @Override
    public void analizarClimas() {

        String alerta = "======================= ALERTA DE CLIMA =======================\n\n";

        Optional<Clima> ultimo = this.climaRepository.findLast();
        Clima clima = ultimo.get();

        if(!humedadAlta(clima.getClimaActual().getHumedad()) && !temperaturaAlta(clima.getClimaActual().getTemperatura())) return;

        alerta += clima.getClimaActual().camposAStrings();

        this.alertaService.enviarAlerta(alerta);

    }

    private boolean temperaturaAlta(double temperatura) {
        return temperatura > 35.0;
    }

    private boolean humedadAlta(int humedad) {
        return humedad > 60;
    }

}
