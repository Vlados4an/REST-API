package ru.erma.restprojectup.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import ru.erma.restprojectup.models.Sensor;

@Getter
@Setter
public class MeasurementAddDTO {
    @NotNull(message = "Temperature shouldn't be empty")
    @Min(value = -100, message = "Temperature should be greater than -100")
    @Max(value = 100, message = "Temperature should be less than 100")
    private Double temperature;

    @NotNull(message = "Humidity shouldn't be empty")
    private Boolean raining;
    @NotNull(message = "Sensor shouldn't be empty")
    private Sensor sensor;
}
