package com.example.ecomai.controller;

import com.example.ecomai.model.Product;
import com.example.ecomai.service.RecommendationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
@CrossOrigin(origins="*")
public class RecommendationController {
    private final RecommendationService recommendationService;
    public RecommendationController(RecommendationService recommendationService){
        this.recommendationService = recommendationService;
    }

    @GetMapping("/{productId}")
    public List<Product> recommend(@PathVariable Long productId, @RequestParam(defaultValue = "4") int limit){
        return recommendationService.recommend(productId, limit);
    }
}
