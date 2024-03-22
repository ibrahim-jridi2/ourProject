package com.modulecomment.exceptions;


public class BadRequestHttpException extends RuntimeException {


    public BadRequestHttpException(String message) {
        super(message);
    }
}
