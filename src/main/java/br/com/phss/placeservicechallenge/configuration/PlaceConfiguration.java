package br.com.phss.placeservicechallenge.configuration;

import br.com.phss.placeservicechallenge.domain.PlaceRepository;
import br.com.phss.placeservicechallenge.domain.PlaceService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;

@Configuration
@EnableR2dbcAuditing
public class PlaceConfiguration {

    @Bean
    PlaceService placeService(PlaceRepository placeRepository) {
        return new PlaceService(placeRepository);
    }
}
