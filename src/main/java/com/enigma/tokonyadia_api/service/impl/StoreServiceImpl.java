package com.enigma.tokonyadia_api.service.impl;

import com.enigma.tokonyadia_api.constant.Constant;
import com.enigma.tokonyadia_api.dto.req.PageReq;
import com.enigma.tokonyadia_api.dto.req.StoreReq;
import com.enigma.tokonyadia_api.dto.res.StoreRes;
import com.enigma.tokonyadia_api.entity.Store;
import com.enigma.tokonyadia_api.repository.StoreRepository;
import com.enigma.tokonyadia_api.service.StoreService;
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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class StoreServiceImpl implements StoreService {
    private final EntityManager entityManager;
    private final ValidationUtil validationUtil;
    private final StoreRepository storeRepository;

    @Override
    public Page<StoreRes> getAll(PageReq req) {
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedFilter");
        filter.setParameter("isDeleted", req.isDeleted());
        Pageable pageable = PageRequest.of(
                req.getPage(),
                req.getSize(),
                SortUtil.parseSort(req.getSort())
        );
        Page<Store> stores = storeRepository.findAll(pageable);
        session.disableFilter("deletedFilter");
        return stores.map(MapperUtil::toStoreRes);
    }

    @Override
    public StoreRes getById(String id) {
        return MapperUtil.toStoreRes(getOne(id));
    }

    @Override
    public Store getOne(String id) {
        return storeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, Constant.ERROR_GET_STORE_MSG));
    }

    @Override
    public StoreRes create(StoreReq req) {
        LogUtil.info("creating store");
        validationUtil.validate(req);
        Store store = Store.builder()
                .siup(req.getSiup())
                .name(req.getName())
                .phone(req.getPhone())
                .address(req.getAddress())
                .build();
        storeRepository.saveAndFlush(store);
        LogUtil.info("finished creating store");
        return MapperUtil.toStoreRes(store);
    }

    @Override
    public StoreRes update(String id, StoreReq req) {
        LogUtil.info("updating store");
        validationUtil.validate(req);
        Store store = getOne(id);
        store.setSiup(req.getSiup());
        store.setName(req.getName());
        store.setPhone(req.getPhone());
        store.setAddress(req.getAddress());
        storeRepository.saveAndFlush(store);
        LogUtil.info("finished updating store");
        return MapperUtil.toStoreRes(store);
    }

    @Override
    public void delete(String id) {
        LogUtil.info("deleting store");
        Store store = getOne(id);
        store.setDeleted(!store.isDeleted());
        storeRepository.save(store);
        LogUtil.info("finished deleting store");
    }
}