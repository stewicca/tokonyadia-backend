package com.enigma.tokonyadia_api.service.impl;

import com.enigma.tokonyadia_api.constant.Constant;
import com.enigma.tokonyadia_api.dto.req.StockReq;
import com.enigma.tokonyadia_api.dto.req.StockSearchReq;
import com.enigma.tokonyadia_api.dto.res.StockRes;
import com.enigma.tokonyadia_api.entity.Product;
import com.enigma.tokonyadia_api.entity.Stock;
import com.enigma.tokonyadia_api.entity.Store;
import com.enigma.tokonyadia_api.repository.StockRepository;
import com.enigma.tokonyadia_api.service.ProductService;
import com.enigma.tokonyadia_api.service.StockService;
import com.enigma.tokonyadia_api.service.StoreService;
import com.enigma.tokonyadia_api.specification.StockSpecification;
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
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class StockServiceImpl implements StockService {
    private final StoreService storeService;
    private final EntityManager entityManager;
    private final ProductService productService;
    private final ValidationUtil validationUtil;
    private final StockRepository stockRepository;

    @Override
    public Page<StockRes> getAll(StockSearchReq req) {
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedFilter");
        filter.setParameter("isDeleted", req.isDeleted());
        Pageable pageable = PageRequest.of(
                req.getPage(),
                req.getSize(),
                SortUtil.parseSort(req.getSort())
        );
        Specification<Stock> specification = StockSpecification.getSpecification(req);
        Page<Stock> stocks = stockRepository.findAll(specification, pageable);
        session.disableFilter("deletedFilter");
        return stocks.map(MapperUtil::toStockRes);
    }

    @Override
    public StockRes getById(String id) {
        return MapperUtil.toStockRes(getOne(id));
    }

    @Override
    public Stock getOne(String id) {
        return stockRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, Constant.ERROR_GET_STOCK_MSG));
    }

    @Override
    public StockRes create(StockReq req) {
        LogUtil.info("creating stock");
        validationUtil.validate(req);
        Store store = storeService.getOne(req.getStoreId());
        Product product = productService.getOne(req.getProductId());
        Stock stock = Stock.builder()
                .store(store)
                .product(product)
                .price(req.getPrice())
                .stock(req.getStock())
                .build();
        stockRepository.saveAndFlush(stock);
        LogUtil.info("finished creating stock");
        return MapperUtil.toStockRes(stock);
    }

    @Override
    public void reduce(String id, Integer quantity) {
        Stock stock = getOne(id);
        if (stock.getStock() < quantity)
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, Constant.ERROR_REDUCE_STOCK_MSG);
        stock.setStock(stock.getStock() - quantity);
        stockRepository.save(stock);
    }

    @Override
    public StockRes update(String id, StockReq req) {
        LogUtil.info("updating stock");
        validationUtil.validate(req);
        Store store = storeService.getOne(req.getStoreId());
        Product product = productService.getOne(req.getProductId());
        Stock stock = getOne(id);
        stock.setStore(store);
        stock.setProduct(product);
        stock.setPrice(req.getPrice());
        stock.setStock(req.getStock());
        stockRepository.saveAndFlush(stock);
        LogUtil.info("finished updating stock");
        return MapperUtil.toStockRes(stock);
    }

    @Override
    public void delete(String id) {
        LogUtil.info("deleting stock");
        Stock stock = getOne(id);
        stock.setDeleted(!stock.isDeleted());
        stockRepository.save(stock);
    }
}