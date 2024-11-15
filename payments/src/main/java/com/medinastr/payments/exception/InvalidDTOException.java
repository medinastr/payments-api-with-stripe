package com.medinastr.payments.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class InvalidDTOException extends RuntimeException {

    private final List<String> errorMessages;
}
