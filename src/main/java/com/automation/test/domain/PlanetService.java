package com.automation.test.domain;

import com.automation.test.repositories.PlanetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<Planet> list() {
        return planetRepository.findAll();
    }

    public void remove(Long id) {
        planetRepository.deleteById(id);
    }
}
