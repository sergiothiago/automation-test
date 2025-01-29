package com.automation.test.repositories;

import com.automation.test.domain.Planet;
import static org.assertj.core.api.Assertions.assertThat;

import com.automation.test.domain.PlanetService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
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

    @AfterEach
    public void afterEach(){
        PLANET.setId(null);
    }

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

        Planet planet = testEntityManager.persistFlushFind(PLANET);

        Optional<Planet> sut = planetRepository.findById(planet.getId());

        assertThat(sut).isNotNull();
        assertThat(sut.get().getName()).isEqualTo(PLANET.getName());
    }

    @Test
    @DisplayName("repository getPlanet_ByUnexistingId_ReturnsPlanet")
    public void getPlanet_ByUnexistingId_ReturnsPlanet(){

        Optional<Planet> sut = planetRepository.findById(999L);

        assertThat(sut).isEmpty();

    }

    @Test
    public void createPlanet_WithExistingName_ThrowsException(){
        Planet planet = testEntityManager.persistFlushFind(PLANET);
        testEntityManager.detach(planet);
        planet.setId(null);

       assertThatThrownBy(() -> planetRepository.save(planet)).isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("repository getPlanet_ByUniexistingName_ReturnsEmpty")
    public void getPlanet_ByUniexistingName_ReturnsEmpty(){

        Optional<Planet> sut = planetRepository.findByName("Quazar Planet");

        assertThat(sut).isEmpty();

    }

    @Test
    @DisplayName("Repository getPlanet_ByExistingName_ReturnsPlanet")
    public void getPlanet_ByExistingName_Returns(){

        Planet planet = testEntityManager.persistFlushFind(PLANET);

        Optional<Planet> sut = planetRepository.findByName("name");

        assertThat(sut).isNotNull();
        assertThat(sut.get().getName()).isEqualTo(PLANET.getName());
    }

    @Test
    @DisplayName("Repository findAll_ByExistingId_ReturnsPlanet")
    public void findAll_Existing_ReturnsPlanet() throws Exception {
        Planet planet = testEntityManager.persistFlushFind(PLANET);

        List<Planet> sut = planetRepository.findAll();
        assertThat(sut).isNotNull();
        assertThat(sut.get(0).getName()).isEqualTo(PLANET.getName());

    }

    @Test
    @DisplayName("Repository remove_ByExistingId_ReturnsPlanet")
    public void remove_ByExistingId_ReturnsPlanet() {
        Planet planet = testEntityManager.persistFlushFind(PLANET);

        planetRepository.deleteById(planet.getId());

        Planet noPlanet = testEntityManager.find(Planet.class, planet.getId());

        assertThat(noPlanet).isNull();
    }

    @Test
    @DisplayName("Repository remove_Unexisting_Id_ThrowsException")
    public void remove_Unexisting_Id_ThrowsException() {
        planetRepository.deleteById(999L);
    }



}
