package ru.erma.restprojectup.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import ru.erma.restprojectup.dto.MeasurementAddDTO;
import ru.erma.restprojectup.models.Measurement;
import ru.erma.restprojectup.models.Sensor;
import ru.erma.restprojectup.services.MeasurementService;
import ru.erma.restprojectup.services.SensorService;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@WebMvcTest(controllers = MeasurementController.class)
@ExtendWith(MockitoExtension.class)
class MeasurementControllerTest {
    @MockBean
    private MeasurementService measurementService;
    @MockBean
    private MeasurementValidator measurementValidator;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private SensorService sensorService;

    @Test
    void getMeasurementsShouldReturnAllMeasurementResponse() throws Exception {
        Measurement measurement = new Measurement();
        measurement.setRaining(true);
        measurement.setTemperature(20.5);
        when(measurementService.findAllMeasurements()).thenReturn(Collections.singletonList(measurement));
        ResultActions response = mockMvc.perform(get("/measurements")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"measurements\":[{\"temperature\":20.5,\"raining\":true}]}")); // Corrected line
    }



    @Test
    void getRainyDaysCountReturnCurrentCount() throws Exception {
        Long rainyDaysCount = 5L;
        when(measurementService.rainyDaysCount()).thenReturn(rainyDaysCount);
        ResultActions response = mockMvc.perform(get("/measurements/rainyDaysCount")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"rainyDaysCount\":5}"));
    }
    @Test
    void getMeasurementReturnsMeasurementWhenExists() throws Exception {
        when(measurementService.findOneMeasurement(1)).thenReturn(new Measurement());
        mockMvc.perform(get("/measurements/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    void getMeasurementReturnsNotFoundWhenNotExists() throws Exception {
        when(measurementService.findOneMeasurement(1)).thenThrow(new SensorNotFoundException());
        mockMvc.perform(get("/measurements/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }



    @Test
    void createMeasurementReturnCreated() throws Exception {
        MeasurementAddDTO measurementDTO = new MeasurementAddDTO();
        measurementDTO.setTemperature(20.0);
        measurementDTO.setRaining(true);
        Sensor sensor = new Sensor();
        sensor.setName("Test sensor");
        measurementDTO.setSensor(sensor);
        doAnswer(invocation -> null).when(measurementService).save(any(Measurement.class));
        ResultActions response = mockMvc.perform(post("/measurements/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(measurementDTO)));
        response.andExpect(status().isOk());
    }


}