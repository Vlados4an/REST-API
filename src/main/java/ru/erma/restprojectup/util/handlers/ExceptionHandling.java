package ru.erma.restprojectup.util.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.erma.restprojectup.util.MeasurementErrorResponse;
import ru.erma.restprojectup.util.MeasurementException;
import ru.erma.restprojectup.util.ApiNotFoundException;

@RestControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler(MeasurementException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MeasurementErrorResponse handleException(MeasurementException e) {
        return createErrorResponse(e);
    }

    @ExceptionHandler(ApiNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MeasurementErrorResponse handleMeasurementNotFoundException(ApiNotFoundException e) {
        return createErrorResponse(e);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MeasurementErrorResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return new MeasurementErrorResponse(
                "Malformed JSON request",
                System.currentTimeMillis()
        );
    }

    private MeasurementErrorResponse createErrorResponse(Exception e) {
        return new MeasurementErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
    }
}