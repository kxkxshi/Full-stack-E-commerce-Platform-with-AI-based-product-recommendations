package com.example.ecomai.service;

import com.example.ecomai.model.CartItem;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CartService {
    // In-memory cart keyed by a simple session id (for demo only)
    private final Map<String, List<CartItem>> carts = new HashMap<>();

    public List<CartItem> getCart(String sessionId){
        return carts.computeIfAbsent(sessionId, k -> new ArrayList<>());
    }

    public List<CartItem> addToCart(String sessionId, CartItem item){
        List<CartItem> cart = getCart(sessionId);
        Optional<CartItem> existing = cart.stream().filter(ci -> Objects.equals(ci.getProductId(), item.getProductId())).findFirst();
        if(existing.isPresent()){
            existing.get().setQuantity(existing.get().getQuantity() + item.getQuantity());
        } else {
            cart.add(item);
        }
        return cart;
    }

    public void clear(String sessionId){
        carts.remove(sessionId);
    }
}
