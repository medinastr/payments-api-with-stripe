package com.medinastr.payments.service;

import com.medinastr.payments.exception.InvalidProductException;
import com.medinastr.payments.model.dto.OrdersDTO;
import com.medinastr.payments.model.entity.Orders;
import com.medinastr.payments.model.entity.Products;
import com.medinastr.payments.repository.OrdersRepository;
import com.medinastr.payments.repository.ProductsRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.Product;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
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

    public String createCheckoutSession(OrdersDTO ordersDTO) throws StripeException {
        Long productId = ordersDTO.getProductsId();
        Optional<Products> optionalProducts = productsRepository.findById(productId);
        if(optionalProducts.isEmpty()) {
            throw new InvalidProductException(productId);
        }
        Products products = optionalProducts.get();
        Orders orders = new Orders(products);
        Orders dbOrders = ordersRepository.save(orders);
        Session session = createSession(dbOrders.getId(), products);
        return session.getUrl();
    }

    private Session createSession(Long orderId, Products products) throws StripeException {
        Product stripeProduct = Product.retrieve(products.getStripeId());
        String priceId = stripeProduct.getDefaultPrice();

        SessionCreateParams sessionCreateParams = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("https://www.cavaco.com.br/")
                .setShippingAddressCollection(
                        SessionCreateParams.ShippingAddressCollection.builder()
                                .addAllowedCountry(SessionCreateParams.ShippingAddressCollection.AllowedCountry.BR)
                                .build()
                )
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPrice(priceId)
                                .build()
                )
                .putMetadata("orderId", String.valueOf(orderId))
                .build();
        return Session.create(sessionCreateParams);
    }
}
