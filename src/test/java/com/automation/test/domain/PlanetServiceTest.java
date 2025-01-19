package com.automation.test.domain;

import com.automation.test.repositories.PlanetRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static com.automation.test.common.PlanetConstants.PLANET;
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

}
