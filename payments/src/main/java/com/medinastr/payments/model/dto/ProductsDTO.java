package com.medinastr.payments.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.transform.Source;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductsDTO implements Source {

    private Long id;

    @NotNull(message = "Name can not be null.")
    @NotBlank(message = "Name can not be null.")
    @Size(min = 2, max = 100, message = "Name needs to have more than 2 characters.")
    private String name;

    @Size(max = 255, message = "Description can not have more than 255 characters.")
    private String description;

    @Min(value = 0, message = "Invalid price.")
    private Float price;

    @Override
    public void setSystemId(String systemId) {

    }

    @Override
    public String getSystemId() {
        return "";
    }
}
