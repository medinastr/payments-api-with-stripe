package com.medinastr.payments.controller;

import com.medinastr.payments.exception.InvalidDTOException;
import com.medinastr.payments.model.dto.ProductsDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Set;

@RestController
public class ProductsRestController {

    @PostMapping("/products")
    public ResponseEntity<ProductsDTO> createProduct(@RequestBody ProductsDTO productsDTO) {
        System.out.println(productsDTO.toString());
        List<String> messages = verifyDTO(productsDTO);
        if(!messages.isEmpty()) {
            throw new InvalidDTOException(messages);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

     private List<String> verifyDTO (ProductsDTO productsDTO) {
         Validator validator;
         try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
             validator = validatorFactory.getValidator();
             Set<ConstraintViolation<ProductsDTO>> violationSet = validator.validate(productsDTO);
             return violationSet.stream()
                     .map(ConstraintViolation::getMessage).toList();
         }
     }
}
