package ru.erma.restprojectup.services;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BeanPropertyBindingResult;
import ru.erma.restprojectup.dto.MeasurementAddDTO;
import ru.erma.restprojectup.dto.MeasurementDTO;
import ru.erma.restprojectup.maper.ModelMapper;
import ru.erma.restprojectup.models.Measurement;
import ru.erma.restprojectup.models.Sensor;
import ru.erma.restprojectup.repositories.MeasurementRepository;
import ru.erma.restprojectup.util.ApiNotFoundException;
import ru.erma.restprojectup.util.MeasurementException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class MeasurementServiceTest {

    @InjectMocks
    private MeasurementService measurementService;

    @Mock
    private MeasurementRepository measurementRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private SensorService sensorService;

    @Test
    public void whenFindAllMeasurements_thenReturnAllMeasurements() {
        // given
        List<Measurement> measurements = Arrays.asList(new Measurement(), new Measurement());
        List<MeasurementDTO> dtos = Arrays.asList(new MeasurementDTO(), new MeasurementDTO());
        when(measurementRepository.findAll()).thenReturn(measurements);
        when(modelMapper.toMeasurementDTOList(measurements)).thenReturn(dtos);

        // when
        List<MeasurementDTO> foundMeasurements = measurementService.findAllMeasurements();

        // then
        assertThat(foundMeasurements.size()).isEqualTo(2);
    }

    @Test
    public void whenFindOneMeasurement_givenMeasurementExists_thenReturnMeasurement() {
        // given
        Measurement measurement = new Measurement();
        when(measurementRepository.findById(1)).thenReturn(Optional.of(measurement));
        when(modelMapper.measurementToDTO(measurement)).thenReturn(new MeasurementDTO());

        // when
        MeasurementDTO foundMeasurement = measurementService.findOneMeasurement(1);

        // then
        assertThat(foundMeasurement).isNotNull();
    }

    @Test
    public void whenFindOneMeasurement_givenMeasurementDoesNotExist_thenThrowApiNotFoundException() {
        // given
        when(measurementRepository.findById(any())).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> measurementService.findOneMeasurement(1))
                .isInstanceOf(ApiNotFoundException.class)
                .hasMessageContaining("Measurement with id = " + 1 + "is not found");
    }

    @Test
    public void whenSaveMeasurement_givenValidMeasurement_thenMeasurementIsSaved() {
        // given
        MeasurementAddDTO measurementDTO = new MeasurementAddDTO();
        Measurement measurement = new Measurement();
        Sensor sensor = new Sensor();
        sensor.setName("test sensor");
        measurement.setSensor(sensor);
        when(modelMapper.measurementAddDTOToMeasurement(measurementDTO)).thenReturn(measurement);
        when(sensorService.findByName("test sensor")).thenReturn(Optional.of(sensor));
        measurementDTO.setTemperature(20.0);
        measurementDTO.setRaining(true);
        measurementDTO.setSensor(sensor);

        // when
        measurementService.saveMeasurement(measurementDTO);

        // then
        verify(measurementRepository, times(1)).save(any());
    }

    @Test
    public void whenSaveMeasurement_givenInvalidMeasurement_thenThrowApiNotFoundException() {
        // given
        MeasurementAddDTO measurementDTO = new MeasurementAddDTO();
        Sensor sensor = new Sensor();
        sensor.setName("test name");
        measurementDTO.setSensor(sensor);

        Measurement measurement = new Measurement();
        measurement.setSensor(sensor);  // Set the Sensor object to the Measurement

        when(sensorService.findByName("test name")).thenReturn(Optional.empty()); // Returns an empty Optional, indicating sensor does not exist
        when(modelMapper.measurementAddDTOToMeasurement(measurementDTO)).thenReturn(measurement); // Returns the Measurement which now has a Sensor object

        // when & then
        assertThatThrownBy(() -> measurementService.saveMeasurement(measurementDTO))
                .isInstanceOf(ApiNotFoundException.class);
    }
}
