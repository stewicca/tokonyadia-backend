package com.enigma.tokonyadia_api.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.enigma.tokonyadia_api.entity.Store;
import com.enigma.tokonyadia_api.service.StoreService;
import com.enigma.tokonyadia_api.repository.StoreRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;

    @Override
    public List<Store> getAll() {
        return storeRepository.findAll();
    }

    @Override
    public Store getById(String id) {
        return storeRepository.findById(id).orElse(null);
    }

    @Override
    public Store create(Store store) {
        return storeRepository.save(store);
    }

    @Override
    public Store update(String id, Store store) {
        Store isStore = getById(id);

        if (isStore == null) return null;

        isStore.setName(store.getName());
        isStore.setAddress(store.getAddress());
        isStore.setPhone(store.getPhone());

        return storeRepository.save(isStore);
    }

    @Override
    public String delete(String id) {
        Store store = getById(id);

        if (store == null) return "Store not found";

        storeRepository.delete(store);

        return "Store deleted";
    }
}