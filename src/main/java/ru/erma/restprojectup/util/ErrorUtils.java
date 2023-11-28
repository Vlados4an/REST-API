package ru.erma.restprojectup.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.stream.Collectors;

public class ErrorUtils {
    public static void returnErrorsToClient(BindingResult bindingResult) {
        String errorMsg = bindingResult
                .getAllErrors()
                .stream().map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining(","));
        throw new MeasurementException(errorMsg);
    }
}
