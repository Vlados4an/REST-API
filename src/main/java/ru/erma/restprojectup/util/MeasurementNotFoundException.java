package ru.erma.restprojectup.util;

public class MeasurementNotFoundException extends RuntimeException{
    public MeasurementNotFoundException(){}

    public MeasurementNotFoundException(String message) {
        super(message);
    }
}
