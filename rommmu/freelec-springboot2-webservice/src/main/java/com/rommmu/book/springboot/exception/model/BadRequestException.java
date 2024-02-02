package com.rommmu.book.springboot.exception.model;


import com.rommmu.book.springboot.exception.ErrorCode;

public class BadRequestException extends CustomException {

    public BadRequestException(ErrorCode error, String message) {
        super(error, message);
    }

}
