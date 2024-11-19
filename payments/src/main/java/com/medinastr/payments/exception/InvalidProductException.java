package com.medinastr.payments.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
@Getter
public class InvalidProductException extends RuntimeException {

    private Long productId;

    public InvalidProductException() {
    }

    public InvalidProductException(String message) {
        super(message);
    }
}
