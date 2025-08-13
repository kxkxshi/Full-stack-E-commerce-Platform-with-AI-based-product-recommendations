package com.example.ecomai.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "orders")
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Instant createdAt = Instant.now();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id")
    private List<OrderItem> items = new ArrayList<>();

    public Long getId(){ return id; }
    public void setId(Long id){ this.id = id; }
    public Instant getCreatedAt(){ return createdAt; }
    public void setCreatedAt(Instant createdAt){ this.createdAt = createdAt; }
    public List<OrderItem> getItems(){ return items; }
    public void setItems(List<OrderItem> items){ this.items = items; }
}
