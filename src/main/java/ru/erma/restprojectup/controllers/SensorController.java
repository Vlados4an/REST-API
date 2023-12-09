package ru.erma.restprojectup.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.erma.restprojectup.dto.SensorDTO;
import ru.erma.restprojectup.services.SensorService;

import static ru.erma.restprojectup.util.ErrorUtils.returnErrorsToClient;

@RestController
@RequestMapping("/sensors")
@RequiredArgsConstructor
public class SensorController {
    private final SensorService sensorService;

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registration(@RequestBody @Valid SensorDTO sensorDTO,
                                                   BindingResult bindingResult){
            if (bindingResult.hasErrors()) {
                returnErrorsToClient(bindingResult);
            }

        sensorService.save(sensorDTO, bindingResult);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
