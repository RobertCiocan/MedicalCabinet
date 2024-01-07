package com.example.mongo_service.utils;

public class CustomException extends RuntimeException{
    public CustomException(String message) {
        super(message);
    }
}
