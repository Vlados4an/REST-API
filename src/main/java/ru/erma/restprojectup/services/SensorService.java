package ru.erma.restprojectup.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.erma.restprojectup.models.Sensor;
import ru.erma.restprojectup.repositories.SensorRepository;

import java.util.Optional;


@Service
@Transactional(readOnly = true)
public class SensorService {
    private final SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }
    @Transactional(readOnly = false)
    public void save(Sensor sensor){
        sensorRepository.save(sensor);
        }

    public Optional<Sensor> findByName(String name) {
        return sensorRepository.findByName(name);
    }
}
