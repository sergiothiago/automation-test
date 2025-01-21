package com.automation.test.repositories;

import com.automation.test.domain.Planet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PlanetRepository extends JpaRepository<Planet, Long> {

    @Query("Select p from Planet p Where p.name = :name")
    Optional<Planet> findByName(String name);
}
