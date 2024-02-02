package com.rommmu.book.springboot.exception.model;


import com.rommmu.book.springboot.exception.ErrorCode;

public class NotFoundException extends CustomException {
    public NotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
