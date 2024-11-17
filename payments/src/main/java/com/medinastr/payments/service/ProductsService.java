package com.medinastr.payments.service;

import com.medinastr.payments.model.dto.ProductsDTO;
import com.medinastr.payments.model.entity.Products;
import com.medinastr.payments.repository.ProductsRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.Product;
import com.stripe.param.ProductCreateParams;
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

    public Products save(ProductsDTO productsDTO) throws StripeException {
        Products products = new Products(productsDTO);

        Product stripeProduct = Product.create(ProductCreateParams.builder()
                .setName(products.getName())
                .setDescription(products.getDescription())
                .setDefaultPriceData(ProductCreateParams.DefaultPriceData.builder()
                        .setCurrency("brl")
                        .setUnitAmount((long)(products.getPrice() * 100))
                        .build()
                )
                .build());

        products.setId(null);
        products.setStripeId(stripeProduct.getId());
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
