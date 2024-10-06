package com.enigma.tokonyadia_api.constant;

public class Constant {
    // Table
    public static final String CUSTOMER_TABLE = "m_customer";
    public static final String PRODUCT_TABLE = "m_product";
    public static final String STORE_TABLE = "m_store";
    public static final String STOCK_TABLE = "t_stock";
    public static final String ORDER_TABLE = "t_order";
    public static final String ORDER_DETAIL_TABLE = "t_order_detail";

    // Router
    public static final String CUSTOMER_API_URL = "/api/customers";
    public static final String PRODUCT_API_URL = "/api/products";
    public static final String STORE_API_URL = "/api/stores";
    public static final String STOCK_API_URL = "/api/stocks";
    public static final String ORDER_API_URL = "/api/orders";
    public static final String ORDER_DETAIL_API_URL = "/api/details";

    // Customer Success Message
    public static final String SUCCESS_GET_ALL_CUSTOMER_MSG = "Successfully retrieve all customers";
    public static final String SUCCESS_GET_CUSTOMER_MSG = "Successfully retrieve customer";
    public static final String SUCCESS_CREATE_CUSTOMER_MSG = "Successfully create customer";
    public static final String SUCCESS_UPDATE_CUSTOMER_MSG = "Successfully update customer";
    public static final String SUCCESS_DELETE_CUSTOMER_MSG = "Successfully delete customer";

    // Customer Error Message
    public static final String ERROR_GET_CUSTOMER_MSG = "Customer not found";

    // Product Success Message
    public static final String SUCCESS_GET_ALL_PRODUCT_MSG = "Successfully retrieve all products";
    public static final String SUCCESS_GET_PRODUCT_MSG = "Successfully retrieve product";
    public static final String SUCCESS_CREATE_PRODUCT_MSG = "Successfully create product";
    public static final String SUCCESS_UPDATE_PRODUCT_MSG = "Successfully update product";
    public static final String SUCCESS_DELETE_PRODUCT_MSG = "Successfully delete product";

    // Customer Error Message
    public static final String ERROR_GET_PRODUCT_MSG = "Product not found";

    // Store Success Message
    public static final String SUCCESS_GET_ALL_STORE_MSG = "Successfully retrieve all stores";
    public static final String SUCCESS_GET_STORE_MSG = "Successfully retrieve store";
    public static final String SUCCESS_CREATE_STORE_MSG = "Successfully create store";
    public static final String SUCCESS_UPDATE_STORE_MSG = "Successfully update store";
    public static final String SUCCESS_DELETE_STORE_MSG = "Successfully delete store";

    // Store Error Message
    public static final String ERROR_GET_STORE_MSG = "Store not found";
}