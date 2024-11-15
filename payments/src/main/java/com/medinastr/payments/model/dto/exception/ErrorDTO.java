package com.medinastr.payments.model.dto.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class ErrorDTO {

    private String endpoint;

    private String message;

    private LocalDateTime timestamp;
}
