package com.automation.test.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private String climate;
    @EqualsAndHashCode.Include
    private String terrain;
    @EqualsAndHashCode.Include
    private String name;
}
