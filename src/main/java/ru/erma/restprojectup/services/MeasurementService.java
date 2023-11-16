package ru.erma.restprojectup.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.erma.restprojectup.models.Measurement;
import ru.erma.restprojectup.repositories.MeasurementRepository;
import ru.erma.restprojectup.util.MeasurementNotFoundException;


import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementService {
    private final MeasurementRepository measurementRepository;

    private final SensorService sensorService;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository,SensorService sensorService) {
        this.measurementRepository = measurementRepository;
        this.sensorService = sensorService;
    }


    public List<Measurement> findAll(){
        return measurementRepository.findAll();
    }
    public Measurement findOne(int id){
        return measurementRepository.findById(id).orElseThrow(MeasurementNotFoundException::new);
        }
    public Long rainyDaysCount(){
        return measurementRepository.countByRaining(true);
        }
    @Transactional(readOnly = false)
    public void save(Measurement measurement){
        enrichMeasurement(measurement);
        measurementRepository.save(measurement);
    }
    private void enrichMeasurement(Measurement measurement) {
        measurement.setSensor(sensorService.findByName(measurement.getSensor().getName()).get());
        measurement.setCreatedAt(LocalDateTime.now());
        measurement.setUpdatedAt(LocalDateTime.now());
    }
    



}
