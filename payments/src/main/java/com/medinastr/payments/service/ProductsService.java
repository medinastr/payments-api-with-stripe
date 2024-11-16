package com.medinastr.payments.service;

import com.medinastr.payments.model.dto.ProductsDTO;
import com.medinastr.payments.model.entity.Products;
import com.medinastr.payments.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public Page<Products> findAll(int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return productsRepository.findAll(pageable);
    }

    public Optional<Products> findById(long id) {
        return productsRepository.findById(id);
    }
}
