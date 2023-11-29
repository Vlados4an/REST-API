package ru.erma.restprojectup.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.erma.restprojectup.dto.MeasurementAddDTO;
import ru.erma.restprojectup.dto.MeasurementDTO;
import ru.erma.restprojectup.maper.ModelMapper;
import ru.erma.restprojectup.models.Measurement;
import ru.erma.restprojectup.repositories.MeasurementRepository;
import ru.erma.restprojectup.util.ApiNotFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MeasurementService {
    private final MeasurementRepository measurementRepository;
    private final ModelMapper modelMapper;
    private final SensorService sensorService;

    public List<MeasurementDTO> findAllMeasurements() {
        return modelMapper
                .toMeasurementDTOList(measurementRepository.findAll());
    }

    public MeasurementDTO findOneMeasurement(int id) {
        Measurement measurement = measurementRepository
                .findById(id).orElseThrow(() ->
                        new ApiNotFoundException("Measurement with id = " + id + "is not found"));
        return modelMapper.measurementToDTO(measurement);
    }

    public Map<String, Long> rainyDaysCount() {
        Long count = measurementRepository.countByRainingTrue();
        Map<String, Long> response = new HashMap<>();
        response.put("rainyDaysCount", count);
        return response;
    }

    @Transactional(readOnly = false)
    public void saveMeasurement(MeasurementAddDTO measurementDTO) {
        Measurement measurementToAdd = modelMapper
                .measurementAddDTOToMeasurement(measurementDTO);

        enrichMeasurement(measurementToAdd);
        measurementRepository.save(measurementToAdd);
    }

    private void enrichMeasurement(Measurement measurement) {
        String sensorName = measurement.getSensor().getName();
        measurement.setSensor(sensorService.findByName(sensorName)
                .orElseThrow(()->new ApiNotFoundException("Sensor with name " + sensorName + " does not exist")));
        measurement.setCreatedAt(LocalDateTime.now());
        measurement.setUpdatedAt(LocalDateTime.now());
    }
}
    