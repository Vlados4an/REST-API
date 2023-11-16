package ru.erma.restprojectup.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.erma.restprojectup.dto.MeasurementAddDTO;
import ru.erma.restprojectup.dto.MeasurementDTO;
import ru.erma.restprojectup.dto.MeasurementResponse;
import ru.erma.restprojectup.models.Measurement;
import ru.erma.restprojectup.services.MeasurementService;
import ru.erma.restprojectup.util.MeasurementErrorResponse;
import ru.erma.restprojectup.util.MeasurementException;
import ru.erma.restprojectup.util.MeasurementNotFoundException;
import ru.erma.restprojectup.util.MeasurementValidator;


import java.util.stream.Collectors;

import static ru.erma.restprojectup.util.ErrorUtils.returnErrorsToClient;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {
    private final MeasurementService measurementService;
    private final ModelMapper modelMapper;

    private final MeasurementValidator measurementValidator;

    @Autowired
    public MeasurementController(MeasurementService measurementService, ModelMapper modelMapper, MeasurementValidator measurementValidator) {
        this.measurementService = measurementService;
        this.modelMapper = modelMapper;
        this.measurementValidator = measurementValidator;
    }

    @GetMapping()
    public MeasurementResponse getMeasurements(){
        return new MeasurementResponse(measurementService.findAll().stream().map(this::convertToMeasurementDTO)
                .collect(Collectors.toList()));
    }

    @GetMapping("/rainyDaysCount")
    public Long getRainyDaysCount(){
        return measurementService.rainyDaysCount();
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getMeasurement(@PathVariable("id") int id){
        try {
            return ResponseEntity.ok(convertToMeasurementDTO(measurementService.findOne(id)));
        } catch (MeasurementNotFoundException e) {
            return new ResponseEntity<>("Measurement with id " + id + " not found", HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/add")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid MeasurementAddDTO measurementDTO,
                                             BindingResult bindingResult){
        Measurement measurementToAdd = convertToMeasurementAdd(measurementDTO);
        measurementValidator.validate(measurementToAdd, bindingResult);
        if(bindingResult.hasErrors())
            returnErrorsToClient(bindingResult);

        measurementService.save(measurementToAdd);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementException e){
        MeasurementErrorResponse response = new MeasurementErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Measurement convertToMeasurementAdd(MeasurementAddDTO measurementDTO) {
        return modelMapper.map(measurementDTO, Measurement.class);
    }
    private MeasurementDTO convertToMeasurementDTO(Measurement measurement){
        return modelMapper.map(measurement, MeasurementDTO.class);
    }
}
