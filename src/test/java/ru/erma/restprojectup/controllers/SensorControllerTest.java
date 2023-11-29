package ru.erma.restprojectup.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.erma.restprojectup.dto.SensorDTO;
import ru.erma.restprojectup.services.SensorService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SensorController.class)
public class SensorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SensorService sensorService;

    @Test
    public void whenRegistration_givenValidSensor_thenStatusIsCreated() throws Exception {
        SensorDTO sensorDTO = new SensorDTO();
        sensorDTO.setName("New Sensor");

        mockMvc.perform(post("/sensors/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(sensorDTO)))
                .andExpect(status().isCreated());
    }
}