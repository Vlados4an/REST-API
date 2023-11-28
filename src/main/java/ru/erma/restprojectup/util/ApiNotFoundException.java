package ru.erma.restprojectup.util;

public class ApiNotFoundException extends RuntimeException {
    public ApiNotFoundException(String msg) {
        super(msg);
    }
}
