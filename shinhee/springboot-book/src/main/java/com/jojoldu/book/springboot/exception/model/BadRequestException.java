package com.jojoldu.book.springboot.exception.model;

import com.jojoldu.book.springboot.exception.ErrorCode;

public class BadRequestException extends CustomException {

    public BadRequestException(ErrorCode error, String message) {
        super(error, message);
    }

}