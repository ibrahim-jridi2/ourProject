package com.campers.now.exceptions;


public class BadRequestHttpException extends RuntimeException {


    public BadRequestHttpException(String message) {
        super(message);
    }
}
