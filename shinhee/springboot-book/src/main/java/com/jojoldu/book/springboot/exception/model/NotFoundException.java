package com.jojoldu.book.springboot.exception.model;

import com.jojoldu.book.springboot.exception.ErrorCode;

public class NotFoundException extends CustomException {
    public NotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
