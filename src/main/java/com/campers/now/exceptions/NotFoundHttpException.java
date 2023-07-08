package com.campers.now.exceptions;


public class NotFoundHttpException extends RuntimeException {


    public NotFoundHttpException(String message) {
        super(message);
    }
}
