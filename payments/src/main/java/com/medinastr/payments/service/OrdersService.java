package com.medinastr.payments.service;

import com.medinastr.payments.model.dto.OrdersDTO;
import com.medinastr.payments.model.entity.Products;
import com.medinastr.payments.repository.OrdersRepository;
import com.medinastr.payments.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrdersService {

    private final OrdersRepository ordersRepository;

    private final ProductsRepository productsRepository;

    @Autowired
    public OrdersService(OrdersRepository ordersRepository, ProductsRepository productsRepository) {
        this.ordersRepository = ordersRepository;
        this.productsRepository = productsRepository;
    }

    public String createCheckoutSession(OrdersDTO ordersDTO){
        Long productId = ordersDTO.getProductsId();
        Optional<Products> optionalProducts = productsRepository.findById(productId);
        if(optionalProducts.isEmpty()) {
            throw new RuntimeException("Product not found.");
        }
        Products products = optionalProducts.get();
        return null;
    }
}
