package com.automation.test.repositories;

import com.automation.test.domain.Planet;
import static org.assertj.core.api.Assertions.assertThat;

import com.automation.test.domain.PlanetService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static com.automation.test.common.PlanetConstants.PLANET;
import static com.automation.test.common.PlanetConstants.INVALID_PLANET;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    @Test
    public void createPlanet_WithInvalidData_ThrowsException(){
        Planet emptyPlanet = new Planet();
        Planet invalidPlanet = INVALID_PLANET;

        assertThatThrownBy( () -> planetRepository.save(emptyPlanet)).isInstanceOf(RuntimeException.class);
        assertThatThrownBy( () -> planetRepository.save(invalidPlanet)).isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("Repository getPlanet_ByExistingId_ReturnsPlanet")
    public void getPlanet_ByExistingId_Returns(){

        Planet planet = testEntityManager.persist(PLANET);

        Optional<Planet> sut = planetRepository.findById(planet.getId());

        assertThat(sut).isNotNull();
        assertThat(sut.get().getName()).isEqualTo(PLANET.getName());
    }


}
