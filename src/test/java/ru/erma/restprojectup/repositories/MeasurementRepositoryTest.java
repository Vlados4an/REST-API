package ru.erma.restprojectup.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.erma.restprojectup.models.Measurement;
import ru.erma.restprojectup.models.Sensor;
import ru.erma.restprojectup.repositories.MeasurementRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class MeasurementRepositoryTest {
    private static final double TEST_TEMPERATURE = 20.0;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MeasurementRepository measurementRepository;
    private Sensor sensor;

    @BeforeEach
    void setUp() {
        sensor = new Sensor();
        sensor.setName("hcp-505");
        entityManager.persist(sensor);
        entityManager.flush();
    }
    @Test
    public void whenCountByRaining_thenReturnCorrectCount() {
        // given
        persistMeasurement(true);
        persistMeasurement(true);
        persistMeasurement(false);
        // when
        Long count = measurementRepository.countByRaining(true);
        // then
        assertThat(count).isEqualTo(2L);
    }

    @Test
    public void whenCountByRaining_thenReturnZero() {
        // given
        persistMeasurement(false);
        persistMeasurement(false);
        // when
        Long count = measurementRepository.countByRaining(true);
        // then
        assertThat(count).isEqualTo(0L);
    }
    private void persistMeasurement(boolean isRaining){
        Measurement measurement = new Measurement();
        measurement.setTemperature(TEST_TEMPERATURE);
        measurement.setSensor(sensor);
        measurement.setRaining(isRaining);
        entityManager.persist(measurement);
    }
}
