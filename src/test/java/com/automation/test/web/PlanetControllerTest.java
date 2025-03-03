package com.automation.test.web;

import com.automation.test.domain.Planet;
import com.automation.test.domain.PlanetService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static com.automation.test.common.PlanetConstants.PLANET;
import static com.automation.test.common.PlanetConstants.INVALID_PLANET;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
@WebMvcTest(PlanetController.class)
public class PlanetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PlanetService planetService;


    @Test
    public void createPlanet_WithValidDate_Returns() throws Exception {

        when(planetService.create(PLANET)).thenReturn(PLANET);

        mockMvc.perform(
                post("/planets")
                        .content(objectMapper.writeValueAsString(PLANET))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$").value(PLANET));
    }

    @Test
    public void createPlanet_WithInvalidData_ReturnsBadRequest() throws Exception {
        Planet emptyPlanet = new Planet();
        Planet invalidPlanet = INVALID_PLANET;

        mockMvc.perform(
                        post("/planets")
                                .content(objectMapper.writeValueAsString(emptyPlanet))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());

        mockMvc.perform(
                        post("/planets")
                                .content(objectMapper.writeValueAsString(invalidPlanet))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void createPlanet_WithExistingName_ReturnsConflict() throws Exception {
        when(planetService.create(any())).thenThrow(DataIntegrityViolationException.class);

        mockMvc.perform(
                        post("/planets")
                                .content(objectMapper.writeValueAsString(PLANET))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());

    }


    @Test
    public void getPlanet_ByExistingId_Returns() throws Exception {

        when(planetService.findById(anyLong())).thenReturn(Optional.of(PLANET));

        mockMvc.perform(
                        get("/planets/1")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(PLANET));
    }

    @Test
    @DisplayName("Integration getPlanet_ByUnexistingId_Returns")
    public void getPlanet_ByUnexistingId_Returns() throws Exception {

        when(planetService.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(
                get("/planets/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    public void getPlanet_ByExistingName_Returns() throws Exception {

        when(planetService.findByName(anyString())).thenReturn(Optional.of(PLANET));

        mockMvc.perform(
                        get("/planets/name/name")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(PLANET));
    }

    @Test
    @DisplayName("Integration getPlanet_ByUnexistingName_Returns")
    public void getPlanet_ByUnexistingName_Returns() throws Exception {

        when(planetService.findByName("quazarPlanet")).thenReturn(Optional.empty());

        mockMvc.perform(
                        get("/planets/name/quazarPlanet")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("Integration findAll_ByExistingId_ReturnsPlanet")
    public void findAll_ByExistingId_ReturnsPlanet() throws Exception {

        when(planetService.list()).thenReturn(Arrays.asList(PLANET));

        mockMvc.perform(
                get("/planets")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value(PLANET.getName()))
                .andExpect(jsonPath("$[0].climate").value(PLANET.getClimate()));

    }




    @Test
    @DisplayName("Controller remove_ByExistingId_ReturnsPlanet")
    public void remove_ByExistingId_ReturnsPlanet() throws Exception {
        doNothing().when(planetService).remove(1L);

        mockMvc.perform(
                        delete("/planets/1")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

    }
}
