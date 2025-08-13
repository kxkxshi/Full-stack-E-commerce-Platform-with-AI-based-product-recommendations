package com.example.ecomai.controller;

import com.example.ecomai.model.CartItem;
import com.example.ecomai.service.CartService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins="*")
public class CartController {
    private final CartService cartService;
    public CartController(CartService cartService){ this.cartService = cartService; }

    private String session(@RequestHeader(value = "X-Session-Id", required = false) String sid){
        return (sid == null || sid.isBlank()) ? "demo-session" : sid;
    }

    @GetMapping
    public List<CartItem> get(@RequestHeader(value = "X-Session-Id", required = false) String sid){
        return cartService.getCart(session(sid));
    }

    @PostMapping
    public List<CartItem> add(@RequestHeader(value = "X-Session-Id", required = false) String sid,
                              @RequestBody CartItem item){
        return cartService.addToCart(session(sid), item);
    }

    @DeleteMapping
    public void clear(@RequestHeader(value = "X-Session-Id", required = false) String sid){
        cartService.clear(session(sid));
    }
}
