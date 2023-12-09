package ru.erma.restprojectup.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import ru.erma.restprojectup.dto.SensorDTO;
import ru.erma.restprojectup.maper.ModelMapper;
import ru.erma.restprojectup.models.Sensor;
import ru.erma.restprojectup.repositories.SensorRepository;
import ru.erma.restprojectup.util.MeasurementException;

import java.util.Optional;

import static ru.erma.restprojectup.util.ErrorUtils.returnErrorsToClient;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SensorService {
    private final SensorRepository sensorRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = false)
    public void save(SensorDTO sensorDTO, BindingResult bindingResult){
        String name = sensorDTO.getName();

        sensorRepository.findByName(name)
                .ifPresent(s -> {
                    throw new MeasurementException("Sensor with name " + name + " already exists");
                });

        Sensor sensorToAdd = modelMapper.sensorDTOToSensor(sensorDTO);
        sensorRepository.save(sensorToAdd);
    }

    public Optional<Sensor> findByName(String name) {
        return sensorRepository.findByName(name);
    }
}