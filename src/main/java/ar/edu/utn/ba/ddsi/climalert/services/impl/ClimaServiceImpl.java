package ar.edu.utn.ba.ddsi.climalert.services.impl;

import ar.edu.utn.ba.ddsi.climalert.config.RestWeatherProperties;
import ar.edu.utn.ba.ddsi.climalert.dtos.Clima;
import ar.edu.utn.ba.ddsi.climalert.services.ClimaService;
import java.net.URI;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class ClimaServiceImpl implements ClimaService {

    private RestTemplate restTemplate;
    private RestWeatherProperties properties;

    public ClimaServiceImpl(RestTemplate restTemplate, RestWeatherProperties properties) {
        this.restTemplate = restTemplate;
        this.properties = properties;
    }

    @Override
    public Clima obtenerTemperaturaYHumedad() {
        URI uri = UriComponentsBuilder
            .fromUriString(properties.getBaseUrl())
            .path(properties.getMethod())
            .queryParam("key", properties.getKey())
            .queryParam("q", properties.getCityName())
            .build()
            .toUri();

        Clima clima = restTemplate.getForObject(uri, Clima.class);

        return clima;
    }

}
