package ru.erma.restprojectup.services;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.erma.restprojectup.models.Measurement;
import ru.erma.restprojectup.models.Sensor;
import ru.erma.restprojectup.repositories.MeasurementRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MeasurementServiceTest {
    @Mock
    private MeasurementRepository measurementRepository;
    @Mock
    private SensorService sensorService;
    @InjectMocks
    private MeasurementService measurementService;
    private Measurement measurement;
    @Test
    public void saveShouldCallRepositorySave(){
        saveTestMeasurement();
        verify(measurementRepository).save(any(Measurement.class));
    }
    @Test
    public void saveShouldEnrichMeasurement(){
        saveTestMeasurement();
        assertThat(measurement.getCreatedAt()).isNotNull();
        assertThat(measurement.getCreatedAt()).isNotNull();
    }
    @Test
    public void findAllShouldReturnAllMeasurements(){
        Measurement measurement1 = new Measurement();
        Measurement measurement2 = new Measurement();
        List<Measurement> expectedMeasurements = List.of(measurement1,measurement2);
        when(measurementRepository.findAll()).thenReturn(List.of(measurement1,measurement2));
        List<Measurement> actualMeasurements = measurementService.findAllMeasurements();
        assertThat(actualMeasurements).isEqualTo(expectedMeasurements);
    }
    @Test
    public void findAllShouldReturnEmptyListWhenNoMeasurement(){
        when(measurementRepository.findAll()).thenReturn(Collections.emptyList());
        List<Measurement> actualMeasurements = measurementService.findAllMeasurements();
        Assertions.assertThat(actualMeasurements).isEmpty();
    }
    @Test
    public void findOneShouldReturnMeasurementWhenExists(){
        int measurementId = 1;
        measurement = new Measurement();
        when(measurementRepository.findById(1)).thenReturn(Optional.ofNullable(measurement));
        Measurement foundMeasurement = measurementService.findOneMeasurement(measurementId);
        assertThat(foundMeasurement).isNotNull();
    }
    @Test
    public void rainyDaysCountShouldReturnCurrentCount(){
        Long count = 2L;
        when(measurementRepository.countByRaining(true)).thenReturn(count);
        count = measurementService.rainyDaysCount();
        assertThat(count).isEqualTo(2L);
    }

    private void saveTestMeasurement(){
        String sensorName = "Test sensor";
        measurement = new Measurement();
        Sensor sensor = new Sensor();
        sensor.setName(sensorName);
        measurement.setSensor(sensor);
        when(sensorService.findByName(sensorName)).thenReturn(Optional.of(sensor));
        measurementService.save(measurement);
    }

}
