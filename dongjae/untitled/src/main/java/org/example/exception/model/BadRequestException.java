package org.example.exception.model;

import org.example.exception.ErrorCode;

public class BadRequestException extends CustomException {

    public BadRequestException(ErrorCode error, String message) {
        super(error, message);
    }

}
