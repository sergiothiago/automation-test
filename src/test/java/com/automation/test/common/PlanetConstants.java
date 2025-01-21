package com.automation.test.common;

import com.automation.test.domain.Planet;

public class PlanetConstants {

    public static final Planet PLANET = Planet.builder()
            .name("name")
            .climate("climate")
            .terrain("terrain")
            .build();

    public static final Planet INVALID_PLANET = Planet.builder()
            .name("")
            .climate("")
            .terrain("")
            .build();
}
