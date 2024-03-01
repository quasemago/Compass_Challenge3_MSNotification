package com.compassuol.sp.challenge.msnotification.infra.mqueue.exception;

public class InvalidNotificationDataException extends RuntimeException {
    public InvalidNotificationDataException(String message) {
        super(message);
    }
}
