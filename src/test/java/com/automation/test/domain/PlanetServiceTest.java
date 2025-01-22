package com.automation.test.domain;

import com.automation.test.repositories.PlanetRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static com.automation.test.common.PlanetConstants.PLANET;
import static com.automation.test.common.PlanetConstants.INVALID_PLANET;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PlanetServiceTest {


    @Mock
    private PlanetRepository planetRepository;

    @InjectMocks
    private PlanetService planetService;

    @Test
    @DisplayName("createPlanet_WithValidData_ReturnsPlanet")
    public void createPlanet_WithValidData_ReturnsPlanet(){

        when(planetRepository.save(PLANET)).thenReturn(PLANET);

        //System under test
        Planet sut = planetService.create(PLANET);

        Assertions.assertThat(sut).isEqualTo(PLANET);
    }

    @Test
    @DisplayName("createPlanet_WithInvalidData_ThrowsException")
    public void createPlanet_WithInvalidData_ThrowsException(){
        when(planetRepository.save(INVALID_PLANET)).thenThrow(RuntimeException.class);

        Assertions.assertThatThrownBy(() ->
                planetService.create(INVALID_PLANET)
                ).isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("getPlanet_ByExistingId_ReturnsPlanet")
    public void getPlanet_ByExistingId_ReturnsPlanet(){
        when(planetRepository.findById(anyLong())).thenReturn(Optional.of(PLANET));

        Long one = 1L;

        Assertions.assertThat(planetService.findById(one)).isNotEmpty();
        Assertions.assertThat(planetService.findById(one).get()).isEqualTo(PLANET);
    }

    @Test
    @DisplayName("getPlanet_ByUnexistingId_ReturnsPlanet")
    public void getPlanet_ByUnexistingId_ReturnsPlanet(){
        when(planetRepository.findById(anyLong())).thenReturn(Optional.empty());

        Long one = 999L;

        Assertions.assertThat(planetService.findById(one)).isEmpty();
    }

    @Test
    @DisplayName("findPlanetByName_ByExistingId_ReturnsPlanet")
    public void findPlanetByName_ByExistingId_ReturnsPlanet(){
        when(planetRepository.findByName(anyString())).thenReturn(Optional.of(PLANET));

        String planetOne = "Earth";

        Assertions.assertThat(planetService.findByName(planetOne)).isNotEmpty();
        Assertions.assertThat(planetService.findByName(planetOne).get()).isEqualTo(PLANET);

    }

    @Test
    @DisplayName("findPlanetByName_ByUnexistingId_ReturnsPlanet")
    public void findPlanetByName_ByUnexistingId_ReturnsPlanet(){
        when(planetRepository.findByName(anyString())).thenReturn(Optional.empty());

        String planetOne = "Earth";

        Assertions.assertThat(planetService.findByName(planetOne)).isEmpty();

    }

    @Test
    @DisplayName("findAll_ByExistingId_ReturnsPlanet")
    public void findAll_ByExistingId_ReturnsPlanet(){
        when(planetRepository.findAll()).thenReturn(Arrays.asList(PLANET));

        Assertions.assertThat(planetService.list()).isNotEmpty();
        Assertions.assertThat(planetService.list().
                stream().
                findFirst().
                get()).isEqualTo(PLANET);
    }


    @Test
    @DisplayName("remove_ByExistingId_ReturnsPlanet")
    public void remove_ByExistingId_ReturnsPlanet() {
        doNothing().when(planetRepository).deleteById(anyLong());

        assertThatCode(() -> planetService.remove(anyLong()))
                .doesNotThrowAnyException();

    }

    @Test
    @DisplayName("remove_Unexisting_Id_ThrowsException")
    public void remove_Unexisting_Id_ThrowsException() {
        doThrow(new RuntimeException()).when(planetRepository).deleteById(anyLong());


        assertThatThrownBy(() -> planetService.remove(anyLong()) )
                .isInstanceOf(RuntimeException.class);

    }



}
