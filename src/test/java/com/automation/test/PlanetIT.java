package com.automation.test;

import com.automation.test.domain.Planet;
import org.assertj.core.api.Assertions;
import org.h2.table.Plan;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Arrays;
import java.util.List;

import static com.automation.test.common.PlanetConstants.PLANET;
@ActiveProfiles("it")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"/import_planets.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
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

    @Test
    public void findByIdPlanet_ReturnsPlanet(){

        //String url, Class<T> responseType, Object... urlVariables
        ResponseEntity<Planet> sut =
                testRestTemplate.getForEntity("/planets/1", Planet.class);

        Assertions.assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(sut.getBody().getName()).isEqualTo("Endor");
    }

    @Test
    public void list_Planets_ReturnsPlanets(){

        ResponseEntity<Planet[]> sut =
                testRestTemplate.getForEntity("/planets", Planet[].class);

        Assertions.assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(sut.getBody()).isNotNull();
        Assertions.assertThat(sut.getBody()).hasSize(4);
    }

    @Test
    public void removeByIdPlanet_ReturnsPlanet(){
        ResponseEntity<Void> sut = testRestTemplate.exchange(
                "/planets/1", HttpMethod.DELETE, null, Void.class);

        Assertions.assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }


}
