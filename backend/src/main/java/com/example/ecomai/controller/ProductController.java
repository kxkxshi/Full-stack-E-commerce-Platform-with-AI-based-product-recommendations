package com.example.ecomai.controller;

import com.example.ecomai.model.Product;
import com.example.ecomai.repo.ProductRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {
    private final ProductRepository repo;
    public ProductController(ProductRepository repo){ this.repo = repo; }

    @GetMapping
    public List<Product> list(){ return repo.findAll(); }

    @GetMapping("/{id}")
    public Product get(@PathVariable Long id){ return repo.findById(id).orElseThrow(); }
}
