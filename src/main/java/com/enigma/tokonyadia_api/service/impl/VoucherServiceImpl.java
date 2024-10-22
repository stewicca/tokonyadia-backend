package com.enigma.tokonyadia_api.service.impl;

import com.enigma.tokonyadia_api.constant.Constant;
import com.enigma.tokonyadia_api.dto.req.PageReq;
import com.enigma.tokonyadia_api.dto.req.VoucherReq;
import com.enigma.tokonyadia_api.dto.res.VoucherRes;
import com.enigma.tokonyadia_api.entity.Voucher;
import com.enigma.tokonyadia_api.repository.VoucherRepository;
import com.enigma.tokonyadia_api.service.VoucherService;
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
public class VoucherServiceImpl implements VoucherService {
    private final EntityManager entityManager;
    private final ValidationUtil validationUtil;
    private final VoucherRepository voucherRepository;

    @Override
    public Page<VoucherRes> getAll(PageReq req) {
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedFilter");
        filter.setParameter("isDeleted", req.isDeleted());
        Pageable pageable = PageRequest.of(
                req.getPage(),
                req.getSize(),
                SortUtil.parseSort(req.getSort())
        );
        Page<Voucher> vouchers = voucherRepository.findAll(pageable);
        session.disableFilter("deletedFilter");
        return vouchers.map(MapperUtil::toVoucherRes);
    }

    @Override
    public VoucherRes getById(String id) {
        return MapperUtil.toVoucherRes(getOne(id));
    }

    @Override
    public Voucher getOne(String id) {
        return voucherRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, Constant.ERROR_VOUCHER_NOT_FOUND_MSG));
    }

    @Override
    public Voucher getByCode(String code) {
        return voucherRepository.findByCodeAndDeletedFalse(code)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, Constant.ERROR_VOUCHER_NOT_FOUND_MSG));
    }

    @Override
    public VoucherRes create(VoucherReq req) {
        LogUtil.info("creating voucher");
        validationUtil.validate(req);
        Voucher voucher = Voucher.builder()
                .code(req.getCode())
                .discount(req.getDiscount())
                .description(req.getDescription())
                .build();
        voucherRepository.saveAndFlush(voucher);
        LogUtil.info("finished creating voucher");
        return MapperUtil.toVoucherRes(voucher);
    }

    @Override
    public VoucherRes update(String id, VoucherReq req) {
        LogUtil.info("updating voucher");
        validationUtil.validate(req);
        Voucher voucher = getOne(id);
        voucher.setCode(req.getCode());
        voucher.setDiscount(req.getDiscount());
        voucher.setDescription(req.getDescription());
        voucherRepository.saveAndFlush(voucher);
        LogUtil.info("finished updating voucher");
        return MapperUtil.toVoucherRes(voucher);
    }

    @Override
    public void delete(String id) {
        LogUtil.info("deleting voucher");
        Voucher voucher = getOne(id);
        voucher.setDeleted(!voucher.isDeleted());
        voucherRepository.save(voucher);
        LogUtil.info("finished deleting voucher");
    }
}