package com.medinastr.payments.service;

import com.medinastr.payments.model.dto.ProductsDTO;
import com.medinastr.payments.model.entity.Products;
import com.medinastr.payments.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsService {

    private final ProductsRepository productsRepository;

    @Autowired
    public ProductsService(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    public Products save(ProductsDTO productsDTO) {
        Products products = new Products(productsDTO);
        products.setId(null);
        return productsRepository.save(products);
    }
    public List<Products> findAll() {
        return productsRepository.findAll();
    }
}
