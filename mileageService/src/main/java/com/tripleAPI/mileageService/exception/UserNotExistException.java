package com.tripleAPI.mileageService.exception;

public class UserNotExistException extends RuntimeException{
    public UserNotExistException() {

    }

    public UserNotExistException(String message) {super(message);}
}
