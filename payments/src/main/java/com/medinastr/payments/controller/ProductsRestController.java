package com.medinastr.payments.controller;

import com.medinastr.payments.exception.InvalidDTOException;
import com.medinastr.payments.model.dto.ProductsDTO;
import com.medinastr.payments.model.entity.Products;
import com.medinastr.payments.service.ProductsService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
public class ProductsRestController {

    private final ProductsService productsService;

    @Autowired
    public ProductsRestController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @PostMapping("/products")
    public ResponseEntity<ProductsDTO> createProduct(@RequestBody ProductsDTO productsDTO) {
        List<String> messages = verifyDTO(productsDTO);
        if(!messages.isEmpty()) {
            throw new InvalidDTOException(messages);
        }
        Products dbProduct = productsService.save(productsDTO);
        ProductsDTO dbProductDTO = new ProductsDTO(dbProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(dbProductDTO);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductsDTO>> findAll() {
        List<Products> products = productsService.findAll();
        List<ProductsDTO> dbProducts = products.stream()
                .map(ProductsDTO::new).toList();
        return ResponseEntity.status(HttpStatus.OK).body(dbProducts);
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
