package com.example.mongo_service.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

public class ValidationUtil {
    public static Map<String, String> getValidationErrors(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return errors;
    }

    public static Map<String, Object> createErrorMessage(String message, Map<String, String> errors) {
        Map<String, Object> error = new HashMap<>();
        error.put("message", message);
        if (errors != null) {
            error.put("errors", errors);
        }
        return error;
    }
}
