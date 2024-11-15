package com.medinastr.payments.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductsDTO {

    private Long id;

    @NotNull @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @Size(max = 255)
    private String description;

    @Min(value = 0)
    private Float price;
}
