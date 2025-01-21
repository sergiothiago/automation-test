package com.automation.test.domain;

import com.automation.test.repositories.PlanetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Service
public class PlanetService {

    @Autowired
    private PlanetRepository planetRepository;

    public Planet create(Planet planet) {
        return planetRepository.save(planet);
    }

    public Optional<Planet> findById(Long id){
         return planetRepository.findById(id);
    }

    public Optional<Planet> findByName(String name){
        return planetRepository.findByName(name);
    }


}
