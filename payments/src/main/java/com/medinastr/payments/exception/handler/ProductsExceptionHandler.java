package com.medinastr.payments.exception.handler;

import com.medinastr.payments.exception.InvalidDTOException;
import com.medinastr.payments.exception.InvalidProductException;
import com.medinastr.payments.model.dto.exception.ErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
public class ProductsExceptionHandler {

    @ExceptionHandler(InvalidDTOException.class)
    public ResponseEntity<String> handlerDTOException(InvalidDTOException exc) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        List<String> messages = exc.getErrorMessages();
        String errorMessage = messages.stream()
                .reduce("", (s1, s2) -> s1 + "\n" + s2);
        ErrorDTO errorDTO = new ErrorDTO(request.getRequestURI(), errorMessage, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(InvalidProductException.class)
    public ResponseEntity<String> handlerProductException(InvalidProductException exc) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String errorMessage = "The product of id " + exc.getProductId() + " was not found.";
        ErrorDTO errorDTO = new ErrorDTO(request.getRequestURI(), errorMessage, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }
}
