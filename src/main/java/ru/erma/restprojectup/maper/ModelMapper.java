package ru.erma.restprojectup.maper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.erma.restprojectup.dto.MeasurementAddDTO;
import ru.erma.restprojectup.dto.MeasurementDTO;
import ru.erma.restprojectup.dto.SensorDTO;
import ru.erma.restprojectup.models.Measurement;
import ru.erma.restprojectup.models.Sensor;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ModelMapper {
    MeasurementDTO measurementToDTO(Measurement measurement);
    Measurement measurementAddDTOToMeasurement(MeasurementAddDTO measurementDTO);
    List<MeasurementDTO> toMeasurementDTOList(List<Measurement> measurementList);

    Sensor sensorDTOToSensor(SensorDTO sensorDTO);
}
