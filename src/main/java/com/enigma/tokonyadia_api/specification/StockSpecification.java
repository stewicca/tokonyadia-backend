package com.enigma.tokonyadia_api.specification;

import com.enigma.tokonyadia_api.dto.req.StockSearchReq;
import com.enigma.tokonyadia_api.entity.Stock;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class StockSpecification {
    public static Specification<Stock> getSpecification(StockSearchReq req) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (req.getStoreId() != null && !req.getStoreId().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("store").get("id"), req.getStoreId()));
            }

            if (req.getCategoryId() != null && !req.getCategoryId().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("product").get("category").get("id"), req.getCategoryId()));
            }

            if (req.getQuery() != null && !req.getQuery().isEmpty()) {
                Predicate namePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("product").get("name")), req.getQuery().toLowerCase() + "%");
                Predicate descriptionPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("product").get("description")), req.getQuery().toLowerCase() + "%");
                predicates.add(criteriaBuilder.or(namePredicate, descriptionPredicate));
            }

            if (predicates.isEmpty()) return criteriaBuilder.conjunction();

            return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
        };
    }
}