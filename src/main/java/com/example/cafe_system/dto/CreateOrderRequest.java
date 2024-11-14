package com.example.cafe_system.dto;

import com.example.cafe_system.model.OrderItem;
import java.util.List;

public class CreateOrderRequest {
    private List<OrderItem> orderItems;

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}