package com.example.cafe_system.service;

import com.example.cafe_system.dto.CreateOrderRequest;
import com.example.cafe_system.model.Order;
import com.example.cafe_system.model.OrderItem;
import com.example.cafe_system.model.User;
import com.example.cafe_system.repository.OrderRepository;
import com.example.cafe_system.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
        
    @Autowired
    public OrderService(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public Order createOrder(HttpServletRequest request, CreateOrderRequest orderRequest) {
        Order order = new Order();
        // TODO: set order other attributes （order info,time ,state...）
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");

        List<OrderItem> orderItems = orderRequest.getOrderItems();
        order.setOrderItems(orderItems);

        // order amount
//        double totalAmount = orderItems.stream()
//                .mapToDouble(item -> item.getMenuItem().getPrice() * item.getQuantity())
//                .sum();
//        order.setTotalAmount(totalAmount);

        // check if the user is logged in
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() &&
                !(authentication instanceof AnonymousAuthenticationToken)) {
            User user = userRepository.findByUsername(authentication.getName());
            order.setUser(user);
        } else {
            // get sessionId
            String sessionId = request.getSession().getId();
            order.setSessionId(sessionId);
        }

        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public Order updateOrder(Long id, Order order) {
        order.setId(id);
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

}