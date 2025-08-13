package com.example.ecomai.controller;

import com.example.ecomai.model.CartItem;
import com.example.ecomai.model.Order;
import com.example.ecomai.model.OrderItem;
import com.example.ecomai.repo.OrderRepository;
import com.example.ecomai.service.CartService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins="*")
public class OrderController {
    private final OrderRepository orderRepository;
    private final CartService cartService;

    public OrderController(OrderRepository orderRepository, CartService cartService){
        this.orderRepository = orderRepository;
        this.cartService = cartService;
    }

    @PostMapping("/checkout")
    public Order checkout(@RequestHeader(value = "X-Session-Id", required = false) String sid){
        List<CartItem> cart = cartService.getCart((sid == null || sid.isBlank()) ? "demo-session" : sid);
        Order order = new Order();
        for(CartItem ci : cart){
            OrderItem oi = new OrderItem();
            oi.setProductId(ci.getProductId());
            oi.setQuantity(ci.getQuantity());
            order.getItems().add(oi);
        }
        Order saved = orderRepository.save(order);
        cartService.clear((sid == null || sid.isBlank()) ? "demo-session" : sid);
        return saved;
    }
}
