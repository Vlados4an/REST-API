package ru.erma.restprojectup.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class SensorDTO {
    @NotEmpty(message = "Name shouldn't be empty")
    @Size(min = 3, max = 30, message = "Name should be between 3 and 30 characters")
    private String name;
}
