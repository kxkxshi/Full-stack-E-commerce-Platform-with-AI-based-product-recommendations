package com.example.ecomai.service;

import com.example.ecomai.model.Product;
import com.example.ecomai.repo.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    private final ProductRepository productRepository;

    public RecommendationService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Simple Jaccard similarity on tag sets
    public List<Product> recommend(Long productId, int limit){
        Optional<Product> opt = productRepository.findById(productId);
        if(opt.isEmpty()) return Collections.emptyList();
        Product target = opt.get();
        Set<String> targetTags = tagSet(target.getTags());

        return productRepository.findAll().stream()
                .filter(p -> !Objects.equals(p.getId(), productId))
                .sorted((a,b) -> {
                    double sa = jaccard(targetTags, tagSet(a.getTags()));
                    double sb = jaccard(targetTags, tagSet(b.getTags()));
                    return Double.compare(sb, sa);
                })
                .limit(limit)
                .collect(Collectors.toList());
    }

    private Set<String> tagSet(String tags){
        if(tags == null || tags.isBlank()) return Collections.emptySet();
        return Arrays.stream(tags.toLowerCase().split("[,;\s]+"))
                .filter(s -> !s.isBlank())
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private double jaccard(Set<String> a, Set<String> b){
        if(a.isEmpty() && b.isEmpty()) return 0.0;
        Set<String> inter = new HashSet<>(a);
        inter.retainAll(b);
        Set<String> uni = new HashSet<>(a);
        uni.addAll(b);
        return uni.isEmpty()? 0.0 : (double) inter.size() / (double) uni.size();
    }
}
