package ru.erma.restprojectup.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.erma.restprojectup.dto.SensorDTO;
import ru.erma.restprojectup.services.SensorService;
import ru.erma.restprojectup.util.MeasurementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SensorController.class)
@ExtendWith(MockitoExtension.class)
public class SensorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SensorService sensorService;

    @MockBean
    private SensorValidator sensorValidator;

    @Autowired
    private ObjectMapper objectMapper;

    private SensorDTO sensorDTO;

    @BeforeEach
    void setUp() {
        sensorDTO = new SensorDTO();
    }

    @Test
    void registrationShouldReturnOkWhenNoErrors() throws Exception {
        SensorDTO sensorDTO = new SensorDTO();
        sensorDTO.setName("petrovich");


        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/sensors/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(sensorDTO)));
        response.andExpect(status().isOk());
    }

    @Test
    void registrationShouldReturnBadRequestWhenValidationFails() throws Exception {

        doThrow(new MeasurementException("Error")).when(sensorValidator).validate(any(), any());

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/sensors/registration")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isBadRequest());
    }
}