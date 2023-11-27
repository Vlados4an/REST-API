package ru.erma.restprojectup.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.erma.restprojectup.models.Sensor;
import ru.erma.restprojectup.repositories.SensorRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class SensorRepositoryTest {


    @Autowired
    private SensorRepository sensorRepository;

    @Test
    public void whenFindByName_thenReturnSensor() {
        // given
        Sensor givenSensor = new Sensor();
        givenSensor.setName("Test Sensor");

        sensorRepository.save(givenSensor);

        // when
        Optional<Sensor> foundSensor = sensorRepository.findByName(givenSensor.getName());

        // then
        assertThat(foundSensor).isPresent();
        assertThat(foundSensor.get().getName()).isEqualTo(givenSensor.getName());
    }

    @Test
    public void whenFindByName_thenReturnEmpty() {
        // given
        String sensorName = "Nonexistent Sensor";

        // when
        Optional<Sensor> foundSensor = sensorRepository.findByName(sensorName);

        // then
        assertThat(foundSensor).isNotPresent();
    }
}