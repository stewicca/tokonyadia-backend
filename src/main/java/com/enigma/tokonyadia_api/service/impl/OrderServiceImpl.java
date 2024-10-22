package com.enigma.tokonyadia_api.service.impl;

import com.enigma.tokonyadia_api.constant.Constant;
import com.enigma.tokonyadia_api.constant.OrderStatus;
import com.enigma.tokonyadia_api.dto.req.OrderCreateReq;
import com.enigma.tokonyadia_api.dto.req.OrderDetailReq;
import com.enigma.tokonyadia_api.dto.req.OrderSearchReq;
import com.enigma.tokonyadia_api.dto.req.OrderUpdateReq;
import com.enigma.tokonyadia_api.dto.res.OrderRes;
import com.enigma.tokonyadia_api.entity.Order;
import com.enigma.tokonyadia_api.entity.OrderDetail;
import com.enigma.tokonyadia_api.entity.Stock;
import com.enigma.tokonyadia_api.entity.Store;
import com.enigma.tokonyadia_api.repository.OrderRepository;
import com.enigma.tokonyadia_api.service.OrderService;
import com.enigma.tokonyadia_api.service.StockService;
import com.enigma.tokonyadia_api.service.StoreService;
import com.enigma.tokonyadia_api.specification.OrderSpecification;
import com.enigma.tokonyadia_api.util.LogUtil;
import com.enigma.tokonyadia_api.util.MapperUtil;
import com.enigma.tokonyadia_api.util.SortUtil;
import com.enigma.tokonyadia_api.util.ValidationUtil;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final StockService stockService;
    private final StoreService storeService;
    private final EntityManager entityManager;
    private final ValidationUtil validationUtil;
    private final OrderRepository orderRepository;

    @Override
    public Page<OrderRes> getAll(OrderSearchReq req) {
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedFilter");
        filter.setParameter("isDeleted", req.isDeleted());
        Pageable pageable = PageRequest.of(
                req.getPage(),
                req.getSize(),
                SortUtil.parseSort(req.getSort())
        );
        Specification<Order> specification = OrderSpecification.getSpecification(req);
        Page<Order> orders = orderRepository.findAll(specification, pageable);
        session.disableFilter("deletedFilter");
        return orders.map(MapperUtil::toOrderRes);
    }

    @Override
    public OrderRes getById(String id) {
        return MapperUtil.toOrderRes(getOne(id));
    }

    @Override
    public Order getOne(String id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, Constant.ERROR_ORDER_NOT_FOUND_MSG));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderRes create(OrderCreateReq req) {
        LogUtil.info("creating order");
        validationUtil.validate(req);
        Store store = storeService.getOne(req.getStoreId());

        Order order = Order.builder()
                .store(store)
                .orderStatus(OrderStatus.COMPLETED)
                .orderDetails(new ArrayList<>())
                .build();

        for (OrderDetailReq detailReq : req.getOrderDetails()) {
            Stock stock = stockService.getOne(detailReq.getStockId());

            OrderDetail orderDetail = OrderDetail.builder()
                    .order(order)
                    .stock(stock)
                    .quantity(detailReq.getQuantity())
                    .price(stock.getPrice())
                    .build();

            order.getOrderDetails().add(orderDetail);
            stockService.reduce(stock.getId(), detailReq.getQuantity());
        }

        orderRepository.saveAndFlush(order);
        LogUtil.info("finished creating order");
        return MapperUtil.toOrderRes(order);
    }

    @Override
    public OrderRes update(String id, OrderUpdateReq req) {
        LogUtil.info("updating order");
        validationUtil.validate(req);
        Order order = getOne(id);
        order.setOrderStatus(OrderStatus.getByName(req.getStatus()));
        orderRepository.saveAndFlush(order);
        LogUtil.info("finished updating order");
        return MapperUtil.toOrderRes(order);
    }

    @Override
    public void delete(String id) {
        LogUtil.info("deleting order");
        Order order = getOne(id);
        order.setDeleted(!order.isDeleted());
        orderRepository.save(order);
        LogUtil.info("finished deleting order");
    }
}