package ru.erma.restprojectup.services;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BeanPropertyBindingResult;
import ru.erma.restprojectup.dto.SensorDTO;
import ru.erma.restprojectup.maper.ModelMapper;
import ru.erma.restprojectup.models.Sensor;
import ru.erma.restprojectup.repositories.SensorRepository;
import ru.erma.restprojectup.util.MeasurementException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@SpringBootTest
public class SensorServiceTest {

    @InjectMocks
    private SensorService sensorService;

    @Mock
    private SensorRepository sensorRepository;

    @Mock
    private ModelMapper modelMapper;

    @Test
    public void whenSave_givenSensorDoesNotExist_thenSensorIsSaved() {
        // given
        SensorDTO sensorDTO = new SensorDTO();
        sensorDTO.setName("New Sensor");
        when(sensorRepository.findByName(sensorDTO.getName())).thenReturn(Optional.empty());
        Sensor sensor = new Sensor();
        when(modelMapper.sensorDTOToSensor(sensorDTO)).thenReturn(sensor);

        // when
        sensorService.save(sensorDTO, new BeanPropertyBindingResult(sensorDTO, "sensorDTO"));

        // then
        verify(sensorRepository, times(1)).save(sensor);
    }

    @Test
    public void whenSave_givenSensorExists_thenThrowMeasurementException() {
        // given
        SensorDTO sensorDTO = new SensorDTO();
        sensorDTO.setName("Existing Sensor");
        when(sensorRepository.findByName(sensorDTO.getName())).thenReturn(Optional.of(new Sensor()));

        // when & then
        assertThatThrownBy(() -> sensorService.save(sensorDTO, new BeanPropertyBindingResult(sensorDTO, "sensorDTO")))
                .isInstanceOf(MeasurementException.class)
                .hasMessageContaining("Sensor with name " + sensorDTO.getName() + " already exists");
    }

    @Test
    public void whenFindByName_givenSensorExists_thenReturnSensor() {
        // given
        String sensorName = "Existing Sensor";
        Sensor sensor = new Sensor();
        when(sensorRepository.findByName(sensorName)).thenReturn(Optional.of(sensor));

        // when
        Optional<Sensor> foundSensor = sensorService.findByName(sensorName);

        // then
        assertThat(foundSensor).isPresent();
        assertThat(foundSensor.get()).isEqualTo(sensor);
    }

    @Test
    public void whenFindByName_givenSensorDoesNotExist_thenReturnEmpty() {
        // given
        String sensorName = "Nonexistent Sensor";
        when(sensorRepository.findByName(sensorName)).thenReturn(Optional.empty());

        // when
        Optional<Sensor> foundSensor = sensorService.findByName(sensorName);

        // then
        assertThat(foundSensor).isNotPresent();
    }
}