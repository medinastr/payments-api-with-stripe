package com.medinastr.payments.exception.handler;

import com.medinastr.payments.exception.InvalidDTOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class ProductsExceptionHandler {

    @ExceptionHandler(InvalidDTOException.class)
    public ResponseEntity<String> handlerDTOException(InvalidDTOException exc) {
        List<String> messages = exc.getErrorMessages();
        String errorMessage = messages.stream()
                .reduce("", (s1, s2) -> s1 + "\n" + s2);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
}
