package com.automation.test.repositories;

import com.automation.test.domain.Planet;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static com.automation.test.common.PlanetConstants.PLANET;

@DataJpaTest
public class PlanetRepositoryTest {

    @Autowired
    private PlanetRepository planetRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void createPlanet_WithValidData_ReturnsPlanet(){
        Planet planet = planetRepository.save(PLANET);

        Planet sut = testEntityManager.find(Planet.class, planet.getId());

        assertThat(sut).isNotNull();
        assertThat(PLANET.getName()).isEqualTo(sut.getName());
        assertThat(PLANET.getTerrain()).isEqualTo(sut.getTerrain());
        assertThat(PLANET.getClimate()).isEqualTo(sut.getClimate());
    }

}
