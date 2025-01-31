package com.automation.test;

import com.automation.test.domain.Planet;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static com.automation.test.common.PlanetConstants.PLANET;

@ActiveProfiles("it")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"/remove_planets.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
public class PlanetIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void createPlanet_ReturnCreated(){

       ResponseEntity<Planet> sut =
               testRestTemplate.postForEntity("/planets", PLANET, Planet.class);


        Assertions.assertThat(sut.getStatusCode())
                .isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(sut.getBody().getId())
                        .isNotNull();
        Assertions.assertThat(sut.getBody().getName())
                .isEqualTo(PLANET.getName());
        Assertions.assertThat(sut.getBody().getClimate())
                .isEqualTo(PLANET.getClimate());
        Assertions.assertThat(sut.getBody().getTerrain())
                .isEqualTo(PLANET.getTerrain());

    }

}
