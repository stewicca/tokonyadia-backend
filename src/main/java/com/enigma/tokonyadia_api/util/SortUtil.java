package com.enigma.tokonyadia_api.util;

import org.springframework.data.domain.Sort;

public class SortUtil {
    public static Sort parseSort(String sort) {
        if (sort != null && !sort.isEmpty())
            return sort.startsWith("-") ? // Condition
                    Sort.by(Sort.Direction.DESC, sort.substring(1)) : // True
                    Sort.by(Sort.Direction.ASC, sort); // False
        return Sort.unsorted();
    }
}