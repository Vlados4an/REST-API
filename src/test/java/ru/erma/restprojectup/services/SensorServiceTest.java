package ru.erma.restprojectup.services;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.erma.restprojectup.models.Sensor;
import ru.erma.restprojectup.repositories.SensorRepository;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class SensorServiceTest {
    @Mock
    private SensorRepository sensorRepository;
    @InjectMocks
    private SensorService sensorService;


    @Test
    void saveShouldCallRepositorySave() {
        Sensor sensor = new Sensor();
        sensorService.save(sensor);

        verify(sensorRepository).save(any(Sensor.class));
    }

    @Test
    void findByNameShouldReturnSensorWhenExists() {
        String sensorName = "Test Sensor";
        Sensor sensor = new Sensor();
        sensor.setName(sensorName);
        when(sensorRepository.findByName(sensorName)).thenReturn(Optional.of(sensor));
        Optional<Sensor> foundSensor = sensorService.findByName(sensorName);
        assertThat(foundSensor).isPresent();
        assertThat(foundSensor.get().getName()).isEqualTo(sensorName);

    }
    @Test
    public void findByNameShouldReturnEmptyWhenDoesNotExists(){
        String nonexistentName = "Nonexistent Sensor";
        when(sensorRepository.findByName(nonexistentName)).thenReturn(Optional.empty());
        Optional<Sensor> foundSensor = sensorService.findByName(nonexistentName);
        assertThat(foundSensor).isNotPresent();
    }
}