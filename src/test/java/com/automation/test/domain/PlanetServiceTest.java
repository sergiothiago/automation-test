package com.automation.test.domain;

import com.automation.test.repositories.PlanetRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.automation.test.common.PlanetConstants.PLANET;
import static com.automation.test.common.PlanetConstants.INVALID_PLANET;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PlanetServiceTest {


    @Mock
    private PlanetRepository planetRepository;

    @InjectMocks
    private PlanetService planetService;

    @Test
    public void createPlanet_WithValidData_ReturnsPlanet(){

        when(planetRepository.save(PLANET)).thenReturn(PLANET);

        //System under test
        Planet sut = planetService.create(PLANET);

        Assertions.assertThat(sut).isEqualTo(PLANET);
    }

    @Test
    public void createPlanet_WithInvalidData_ThrowsException(){
        when(planetRepository.save(INVALID_PLANET)).thenThrow(RuntimeException.class);

        Assertions.assertThatThrownBy(() ->
                planetService.create(INVALID_PLANET)
                ).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void getPlanet_ByExistingId_ReturnsPlanet(){
        when(planetRepository.findById(anyLong())).thenReturn(Optional.of(PLANET));

        Long one = 1L;

        Assertions.assertThat(planetService.findById(one)).isNotEmpty();
        Assertions.assertThat(planetService.findById(one).get()).isEqualTo(PLANET);
    }

    @Test
    public void getPlanet_ByUnexistingId_ReturnsPlanet(){
        when(planetRepository.findById(anyLong())).thenReturn(Optional.empty());

        Long one = 999L;

        Assertions.assertThat(planetService.findById(one)).isEmpty();
    }



}
