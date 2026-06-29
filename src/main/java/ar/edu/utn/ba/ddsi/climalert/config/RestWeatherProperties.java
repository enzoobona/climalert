package ar.edu.utn.ba.ddsi.climalert.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "weather-api")
@Data
public class RestWeatherProperties {

    private String baseUrl;
    private String method;
    private String key;
    private String cityName;

}
