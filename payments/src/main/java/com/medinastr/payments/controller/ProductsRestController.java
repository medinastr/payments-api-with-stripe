package com.medinastr.payments.controller;

import com.medinastr.payments.exception.InvalidDTOException;
import com.medinastr.payments.model.dto.ProductsCollectionDTO;
import com.medinastr.payments.model.dto.ProductsDTO;
import com.medinastr.payments.model.entity.Products;
import com.medinastr.payments.service.ProductsService;
import com.stripe.exception.StripeException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
public class ProductsRestController {

    private final ProductsService productsService;

    @Autowired
    public ProductsRestController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @PostMapping("/products")
    public ResponseEntity<ProductsDTO> createProduct(@RequestBody ProductsDTO productsDTO) throws StripeException {
        List<String> messages = verifyDTO(productsDTO);
        if(!messages.isEmpty()) {
            throw new InvalidDTOException(messages);
        }
        Products dbProduct = productsService.save(productsDTO);
        ProductsDTO dbProductDTO = new ProductsDTO(dbProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(dbProductDTO);
    }

    @GetMapping("/products")
    public ResponseEntity<ProductsCollectionDTO> findAll(@RequestParam Integer page) {
        Page<Products> products = productsService.findAll(page);
        List<ProductsDTO> productsDTOList = products.get()
                .map(ProductsDTO::new).toList();

        ProductsCollectionDTO response = new ProductsCollectionDTO();
        response.setCurrent(page);
        response.setPages(products.getTotalPages());
        response.setProductsDTOList(productsDTOList);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductsDTO> findById(@PathVariable("id") Long id) {
        Optional<Products> optionalProducts = productsService.findById(id);
        if(optionalProducts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Products products = optionalProducts.get();
        ProductsDTO response = new ProductsDTO(products);
        return ResponseEntity.status(HttpStatus.OK).body(response);
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
