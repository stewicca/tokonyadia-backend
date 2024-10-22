package com.enigma.tokonyadia_api.specification;

import com.enigma.tokonyadia_api.constant.OrderStatus;
import com.enigma.tokonyadia_api.dto.req.OrderSearchReq;
import com.enigma.tokonyadia_api.entity.Order;
import com.enigma.tokonyadia_api.entity.OrderDetail;
import com.enigma.tokonyadia_api.entity.Product;
import com.enigma.tokonyadia_api.entity.Stock;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class OrderSpecification {
    public static Specification<Order> getSpecification(OrderSearchReq req) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (req.getStoreId() != null && !req.getStoreId().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("store").get("id"), req.getStoreId()));
            }

            if (req.getProductId() != null && !req.getProductId().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.join("orderDetails").join("stock").get("product").get("id"), req.getProductId()));
            }

            if (req.getCustomerId() != null && !req.getCustomerId().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("customer").get("id"), req.getCustomerId()));
            }

            if (req.getVoucherId() != null && !req.getVoucherId().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("voucher").get("id"), req.getVoucherId()));
            }

            if (req.getOrderStatus() != null && !req.getOrderStatus().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("orderStatus"), OrderStatus.getByName(req.getOrderStatus())));
            }

            if (req.getQuery() != null && !req.getQuery().isEmpty()) {
                Join<Order, OrderDetail> orderDetailJoin = root.join("orderDetails");
                Join<OrderDetail, Stock> stockJoin = orderDetailJoin.join("stock");
                Join<Stock, Product> productJoin = stockJoin.join("product");

                Predicate productNamePredicate = criteriaBuilder.like(criteriaBuilder.lower(productJoin.get("name")), req.getQuery().toLowerCase() + "%");
                Predicate customerNamePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("customer").get("name")), req.getQuery().toLowerCase() + "%");

                predicates.add(criteriaBuilder.or(productNamePredicate, customerNamePredicate));
            }

            if (predicates.isEmpty()) return criteriaBuilder.conjunction();

            return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
        };
    }
}