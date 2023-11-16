package ru.erma.restprojectup.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Entity
@Table(name = "sensor")
@Getter
@Setter
public class Sensor {

    @Id
    @GeneratedValue
    private Integer id;

    @NotEmpty(message = "Name shouldn't be empty")
    @Size(min = 3, max = 30, message = "Name should be between 3 and 30 characters")
    private String name;

    @OneToMany(mappedBy = "sensor")
    private List<Measurement> measurements;


}
