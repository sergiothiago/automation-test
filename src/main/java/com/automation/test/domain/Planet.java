package com.automation.test.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "planet")
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Planet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @EqualsAndHashCode.Include
    @Column(name = "climate", nullable = false)
    @NotEmpty
    private String climate;

    @EqualsAndHashCode.Include
    @Column(name = "terrain", nullable = false)
    @NotEmpty
    private String terrain;
    @EqualsAndHashCode.Include
    @Column(name = "name", nullable = false)
    @NotEmpty
    private String name;

    public Planet(String climate, String terrain) {
        this.climate = climate;
        this.terrain = terrain;
    }
}
