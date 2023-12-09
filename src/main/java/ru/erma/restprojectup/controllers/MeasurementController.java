package ru.erma.restprojectup.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.erma.restprojectup.dto.MeasurementAddDTO;
import ru.erma.restprojectup.dto.MeasurementDTO;
import ru.erma.restprojectup.dto.MeasurementResponse;
import ru.erma.restprojectup.services.MeasurementService;


import java.util.Map;

import static ru.erma.restprojectup.util.ErrorUtils.returnErrorsToClient;


@RestController
@RequestMapping("/measurements")
@RequiredArgsConstructor
public class MeasurementController {
    private final MeasurementService measurementService;

    @GetMapping()
    public MeasurementResponse getMeasurements(){
        return new MeasurementResponse(measurementService.findAllMeasurements());
    }

    @GetMapping("/rainyDaysCount")
    public ResponseEntity<Map<String, Long>> getRainyDaysCount(){
        return ResponseEntity.ok(measurementService.rainyDaysCount());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MeasurementDTO> getMeasurement(@PathVariable("id") int id){
        return ResponseEntity.ok(measurementService.findOneMeasurement(id));
    }


    @PostMapping("/add")
    public ResponseEntity<HttpStatus> createMeasurement(@RequestBody @Valid MeasurementAddDTO measurementDTO,
                                                        BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            returnErrorsToClient(bindingResult);
        }

        measurementService.saveMeasurement(measurementDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
