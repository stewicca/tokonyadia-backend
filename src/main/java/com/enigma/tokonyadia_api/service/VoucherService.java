package com.enigma.tokonyadia_api.service;

import com.enigma.tokonyadia_api.dto.req.PageReq;
import com.enigma.tokonyadia_api.dto.req.VoucherReq;
import com.enigma.tokonyadia_api.dto.res.VoucherRes;
import com.enigma.tokonyadia_api.entity.Voucher;
import org.springframework.data.domain.Page;

public interface VoucherService {
    Page<VoucherRes> getAll(PageReq req);

    VoucherRes getById(String id);

    Voucher getOne(String id);

    Voucher getByCode(String code);

    VoucherRes create(VoucherReq req);

    VoucherRes update(String id, VoucherReq req);

    void delete(String id);
}