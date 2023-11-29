package ru.erma.restprojectup.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.erma.restprojectup.dto.MeasurementAddDTO;
import ru.erma.restprojectup.dto.MeasurementDTO;
import ru.erma.restprojectup.models.Sensor;
import ru.erma.restprojectup.services.MeasurementService;
import ru.erma.restprojectup.util.ApiNotFoundException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MeasurementController.class)
public class MeasurementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MeasurementService measurementService;

    @Test
    public void whenCreateMeasurement_givenValidMeasurement_thenStatusIsCreated() throws Exception {
        MeasurementAddDTO measurementDTO = new MeasurementAddDTO();
        Sensor sensor = new Sensor();
        sensor.setName("some");
        measurementDTO.setTemperature(20.0);
        measurementDTO.setRaining(true);
        measurementDTO.setSensor(sensor);


        mockMvc.perform(post("/measurements/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(measurementDTO)))
                .andExpect(status().isCreated());

        verify(measurementService, times(1)).saveMeasurement(any());
    }

    @Test
    public void whenCreateMeasurement_givenInvalidMeasurement_thenStatusIsBadRequest() throws Exception {
        MeasurementAddDTO measurementDTO = new MeasurementAddDTO();

        mockMvc.perform(post("/measurements/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(measurementDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenGetMeasurements_thenReturnAllMeasurements() throws Exception {
        when(measurementService.findAllMeasurements()).thenReturn(Arrays.asList(new MeasurementDTO(), new MeasurementDTO()));

        mockMvc.perform(get("/measurements")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetRainyDaysCount_thenReturnRainyDaysCount() throws Exception {
        Map<String, Long> rainyDaysCount = new HashMap<>();
        rainyDaysCount.put("rainyDaysCount", 5L);
        when(measurementService.rainyDaysCount()).thenReturn(rainyDaysCount);

        mockMvc.perform(get("/measurements/rainyDaysCount")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetMeasurement_givenMeasurementExists_thenReturnMeasurement() throws Exception {
        when(measurementService.findOneMeasurement(1)).thenReturn(new MeasurementDTO());

        mockMvc.perform(get("/measurements/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetMeasurement_givenMeasurementDoesNotExist_thenStatusIsNotFound() throws Exception {
        when(measurementService.findOneMeasurement(1)).thenThrow(new ApiNotFoundException(any()));

        mockMvc.perform(get("/measurements/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}