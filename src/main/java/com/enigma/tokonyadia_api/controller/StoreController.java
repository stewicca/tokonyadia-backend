package com.enigma.tokonyadia_api.controller;

import lombok.AllArgsConstructor;
import com.enigma.tokonyadia_api.entity.Store;
import org.springframework.web.bind.annotation.*;
import com.enigma.tokonyadia_api.constant.Constant;
import com.enigma.tokonyadia_api.service.StoreService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(path = Constant.STORE_API_URL)
public class StoreController {
    private final StoreService storeService;

    @GetMapping
    public List<Store> getAllStore() {
        return storeService.getAll();
    }

    @GetMapping(path = "/{id}")
    public Store getStoreById(@PathVariable String id) {
        return storeService.getById(id);
    }

    @PostMapping
    public Store createStore(@RequestBody Store store) {
        return storeService.create(store);
    }

    @PutMapping(path = "/{id}")
    public Store updateStore(@PathVariable String id, @RequestBody Store store) {
        return storeService.update(id, store);
    }

    @DeleteMapping(path = "/{id}")
    public String deleteStore(@PathVariable String id) {
        return storeService.delete(id);
    }
}