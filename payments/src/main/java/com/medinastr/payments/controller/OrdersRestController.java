package com.medinastr.payments.controller;

import com.medinastr.payments.model.dto.OrdersDTO;
import com.medinastr.payments.service.OrdersService;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrdersRestController {

    private final OrdersService ordersService;

    @Autowired
    public OrdersRestController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @PostMapping("/orders")
    public ResponseEntity<String> createOrder(@RequestBody OrdersDTO ordersDTO) throws StripeException {
        String url = ordersService.createCheckoutSession(ordersDTO);
        return ResponseEntity.status(HttpStatus.OK).body(url);
    }
}
