package com.modulecomment.exceptions;


public class UnAuthorizedHttpException extends RuntimeException {


    public UnAuthorizedHttpException(String message) {
        super(message);
    }
}
