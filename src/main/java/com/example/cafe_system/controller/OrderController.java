package com.example.cafe_system.controller;

import com.example.cafe_system.dto.CreateOrderRequest;
import com.example.cafe_system.model.Order;
import com.example.cafe_system.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) { this.orderService = orderService; }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PostMapping
    public Order createOrder(HttpServletRequest request, @RequestBody CreateOrderRequest orderRequest) {
        return orderService.createOrder(request, orderRequest);
    }

}
