package ar.edu.utn.ba.ddsi.climalert.services.impl;

import ar.edu.utn.ba.ddsi.climalert.config.RestWeatherProperties;
import ar.edu.utn.ba.ddsi.climalert.dtos.Clima;
import ar.edu.utn.ba.ddsi.climalert.dtos.ClimaActual;
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

        String alerta = "Alerta: ";

        Optional<Clima> ultimo = this.climaRepository.findLast();
        ClimaActual clima = ultimo.get().getClimaActual();

        alerta += temperaturaAlta(clima.getTemperatura());
        if(!alerta.equals("Alerta: ")) alerta += " y ";
        alerta += humedadAlta(clima.getHumedad());
        alerta += ".";

        this.alertaService.enviarAlerta(alerta);

    }

    private String temperaturaAlta(double temperatura) {
        return temperatura > 35.0 ? "Temperatura de " + temperatura + " grados " : "";
    }

    private String humedadAlta(int humedad) {
        return humedad > 60 ? "Humedad de " + humedad + " % " : "";
    }

}
