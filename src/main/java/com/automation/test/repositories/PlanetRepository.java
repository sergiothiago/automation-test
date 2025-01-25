package com.automation.test.repositories;

import com.automation.test.domain.Planet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlanetRepository extends  JpaRepository<Planet, Long> {

    @Query("Select p from Planet p Where p.name = :name")
    Optional<Planet> findByName(String name);



}
