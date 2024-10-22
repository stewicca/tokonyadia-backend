package com.enigma.tokonyadia_api.util;

import com.enigma.tokonyadia_api.dto.res.PageInfo;
import com.enigma.tokonyadia_api.dto.res.PageRes;
import com.enigma.tokonyadia_api.dto.res.Res;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class ResUtil {
    public static <T> ResponseEntity<?> buildRes(HttpStatus status, String message, T data) {
        Res<T> body = Res.<T>builder()
                .status(status.value())
                .message(message)
                .data(data)
                .build();
        return ResponseEntity.status(status).body(body);
    }

    public static <T> ResponseEntity<?> buildPageRes(HttpStatus status, String message, Page<T> page) {
        PageInfo pageInfo = PageInfo.builder()
                .total(page.getTotalElements())
                .pages(page.getTotalPages())
                .page(page.getPageable().getPageNumber() + 1)
                .size(page.getSize())
                .build();
        PageRes<List<T>> res = PageRes.<List<T>>builder()
                .status(status.value())
                .message(message)
                .data(page.getContent())
                .pagination(pageInfo)
                .build();
        return ResponseEntity.status(status).body(res);
    }
}