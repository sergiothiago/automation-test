package com.automation.test.domain;

import com.automation.test.repositories.PlanetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlanetService {

    @Autowired
    private PlanetRepository planetRepository;

    public Planet create(Planet planet) {
        return planetRepository.save(planet);
    }

}
