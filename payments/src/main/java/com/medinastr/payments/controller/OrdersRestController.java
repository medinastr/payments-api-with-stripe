package com.medinastr.payments.controller;

import com.medinastr.payments.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrdersRestController {

    private final OrdersService ordersService;

    @Autowired
    public OrdersRestController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }
}
