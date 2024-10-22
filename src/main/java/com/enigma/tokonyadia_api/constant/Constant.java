package com.enigma.tokonyadia_api.constant;

public class Constant {
    public static final String REFRESH_TOKEN_COOKIE_NAME = "refreshToken";

    // Table
    public static final String FILE_TABLE = "m_file";
    public static final String ROLE_TABLE = "m_role";
    public static final String USER_TABLE = "m_user";
    public static final String CUSTOMER_TABLE = "m_customer";
    public static final String CUSTOMER_ADDRESS_TABLE = "m_customer_address";
    public static final String CATEGORY_TABLE = "m_category";
    public static final String CATEGORY_IMAGE_TABLE = "m_category_image";
    public static final String PRODUCT_TABLE = "m_product";
    public static final String PRODUCT_IMAGE_TABLE = "m_product_image";
    public static final String STORE_TABLE = "m_store";
    public static final String VOUCHER_TABLE = "m_voucher";
    public static final String STOCK_TABLE = "t_stock";
    public static final String ORDER_TABLE = "t_order";
    public static final String PAYMENT_TABLE = "t_payment";
    public static final String ORDER_DETAIL_TABLE = "t_order_detail";

    // Router
    public static final String IMAGE_API_URL = "/api/images";
    public static final String ROLE_API_URL = "/api/roles";
    public static final String USER_API_URL = "/api/users";
    public static final String AUTH_API_URL = "/api/auth";
    public static final String CUSTOMER_API_URL = "/api/customers";
    public static final String CATEGORY_API_URL = "/api/categories";
    public static final String PRODUCT_API_URL = "/api/products";
    public static final String STORE_API_URL = "/api/stores";
    public static final String STOCK_API_URL = "/api/stocks";
    public static final String VOUCHER_API_URL = "/api/vouchers";
    public static final String ORDER_API_URL = "/api/orders";
    public static final String PAYMENT_API_URL = "/api/payments";

    // Common Error Message
    public static final String ERROR_UNAUTHORIZED_MSG = "Unauthorized";

    // IO Error Message
    public static final String ERROR_WHILE_CREATING_DIRECTORY = "Error while creating directory";
    public static final String ERROR_WHILE_SAVE_FILE = "Error while save file";
    public static final String ERROR_FILE_NOT_FOUND = "File not found";

    // Role Success Message
    public static final String SUCCESS_GET_All_ROLE_MSG = "Successfully retrieve all roles";
    public static final String SUCCESS_GET_ROLE_MSG = "Successfully get role";
    public static final String SUCCESS_CREATE_ROLE_MSG = "Successfully create role";
    public static final String SUCCESS_UPDATE_ROLE_MSG = "Successfully update role";
    public static final String SUCCESS_DELETE_ROLE_MSG = "Successfully delete role";

    // Role Error Message
    public static final String ERROR_GET_ROLE_MSG = "Role not found";

    // User Success Message
    public static final String SUCCESS_GET_ALL_USER_MSG = "Successfully retrieve all user";
    public static final String SUCCESS_GET_USER_MSG = "Successfully get user";
    public static final String SUCCESS_CREATE_USER_MSG = "Successfully create user";
    public static final String SUCCESS_CHANGE_PASSWORD_MSG = "Successfully change password";
    public static final String SUCCESS_UPDATE_USER_MSG = "Successfully update user";
    public static final String SUCCESS_DELETE_USER_MSG = "Successfully delete user";

    // User Error Message
    public static final String ERROR_GET_USER_MSG = "User not found";
    public static final String ERROR_VERIFY_PASSWORD_MSG = "Old password invalid";
    public static final String ERROR_USER_HAS_BEEN_DELETED_MSG = "Your account has been deleted";

    // Customer Success Message
    public static final String SUCCESS_GET_ALL_CUSTOMER_MSG = "Successfully retrieve all customers";
    public static final String SUCCESS_GET_CUSTOMER_MSG = "Successfully retrieve customer";
    public static final String SUCCESS_CREATE_CUSTOMER_MSG = "Successfully create customer";
    public static final String SUCCESS_UPDATE_CUSTOMER_MSG = "Successfully update customer";
    public static final String SUCCESS_DELETE_CUSTOMER_MSG = "Successfully delete customer";

    // Customer Error Message
    public static final String ERROR_GET_CUSTOMER_MSG = "Customer not found";

    // Customer Address Message
    public static final String SUCCESS_CREATE_CUSTOMER_ADDRESS_MSG = "Successfully add customer address";
    public static final String SUCCESS_UPDATE_CUSTOMER_ADDRESS_MSG = "Successfully update customer address";
    public static final String SUCCESS_DELETE_CUSTOMER_ADDRESS_MSG = "Successfully delete customer address";

    // Customer Address Error Message
    public static final String ERROR_CUSTOMER_ADDRESS_NOT_FOUND_MSG = "Customer address not found";

    // Auth Success Message
    public static final String SUCCESS_REGISTER_MSG = "Successfully register";
    public static final String SUCCESS_LOGIN_MSG = "Successfully login";
    public static final String SUCCESS_LOGOUT_MSG = "Successfully logout";
    public static final String SUCCESS_REFRESH_TOKEN_MSG = "Successfully refreshing token";

    // JWT Error Message
    public static final String ERROR_GENERATE_TOKEN_MSG = "Error generating token";
    public static final String ERROR_VERIFY_TOKEN_MSG = "Error verify token";

    // Refresh Token Error Message
    public static final String ERROR_REFRESH_TOKEN_NOT_FOUND_MSG = "Refresh token not found";
    public static final String ERROR_REFRESH_TOKEN_INVALID_MSG = "Refresh token invalid";

    // Category Success Message
    public static final String SUCCESS_GET_ALL_CATEGORY_MSG = "Successfully retrieve all category";
    public static final String SUCCESS_GET_CATEGORY_MSG = "Successfully get category";
    public static final String SUCCESS_CREATE_CATEGORY_MSG = "Successfully create category";
    public static final String SUCCESS_UPDATE_CATEGORY_MSG = "Successfully update category";
    public static final String SUCCESS_DELETE_CATEGORY_MSG = "Successfully delete category";

    // Category Error Message
    public static final String ERROR_GET_CATEGORY_MSG = "Category not found";

    // Category Image Success Message
    public static final String SUCCESS_UPDATE_CATEGORY_IMAGE_MSG = "Successfully update category image";
    public static final String SUCCESS_DELETE_CATEGORY_IMAGE_MSG = "Successfully delete category image";

    // Category Image Error Message
    public static final String ERROR_CATEGORY_IMAGE_NOT_FOUND_MSG = "Category image not found";

    // Product Success Message
    public static final String SUCCESS_GET_ALL_PRODUCT_MSG = "Successfully retrieve all products";
    public static final String SUCCESS_GET_PRODUCT_MSG = "Successfully retrieve product";
    public static final String SUCCESS_CREATE_PRODUCT_MSG = "Successfully create product";
    public static final String SUCCESS_UPDATE_PRODUCT_MSG = "Successfully update product";
    public static final String SUCCESS_DELETE_PRODUCT_MSG = "Successfully delete product";

    // Product Error Message
    public static final String ERROR_GET_PRODUCT_MSG = "Product not found";

    // Product Image Success Message
    public static final String SUCCESS_UPDATE_PRODUCT_IMAGE_MSG = "Successfully update product image";
    public static final String SUCCESS_DELETE_PRODUCT_IMAGE_MSG = "Successfully delete product image";

    // Product Image Error Message
    public static final String ERROR_PRODUCT_IMAGE_NOT_FOUND_MSG = "Product image not found";

    // Store Success Message
    public static final String SUCCESS_GET_ALL_STORE_MSG = "Successfully retrieve all stores";
    public static final String SUCCESS_GET_STORE_MSG = "Successfully get store";
    public static final String SUCCESS_CREATE_STORE_MSG = "Successfully create store";
    public static final String SUCCESS_UPDATE_STORE_MSG = "Successfully update store";
    public static final String SUCCESS_DELETE_STORE_MSG = "Successfully delete store";

    // Store Error Message
    public static final String ERROR_GET_STORE_MSG = "Store not found";

    // Voucher Success Message
    public static final String SUCCESS_GET_ALL_VOUCHER_MSG = "Successfully get all voucher";
    public static final String SUCCESS_GET_VOUCHER_MSG = "Successfully get voucher";
    public static final String SUCCESS_CREATE_VOUCHER_MSG = "Successfully create voucher";
    public static final String SUCCESS_UPDATE_VOUCHER_MSG = "Successfully update voucher";
    public static final String SUCCESS_DELETE_VOUCHER_MSG = "Successfully delete voucher";

    // Voucher Error Message
    public static final String ERROR_VOUCHER_NOT_FOUND_MSG = "Voucher not found";

    // Stock Success Message
    public static final String SUCCESS_GET_ALL_STOCK_MSG = "Successfully get all stock";
    public static final String SUCCESS_GET_STOCK_MSG = "Successfully get stock";
    public static final String SUCCESS_CREATE_STOCK_MSG = "Successfully create stock";
    public static final String SUCCESS_UPDATE_STOCK_MSG = "Successfully update stock";
    public static final String SUCCESS_DELETE_STOCK_MSG = "Successfully delete stock";

    // Stock Error Message
    public static final String ERROR_GET_STOCK_MSG = "Stock not found";
    public static final String ERROR_REDUCE_STOCK_MSG = "Quantity exceeds available stock";

    // Cart Success Message
    public static final String SUCCESS_ADD_PRODUCT_MSG = "Successfully add product";
    public static final String SUCCESS_INCREASE_PRODUCT_QUANTITY_MSG = "Successfully increase product quantity";
    public static final String SUCCESS_DECREASE_PRODUCT_QUANTITY_MSG = "Successfully decrease product quantity";
    public static final String SUCCESS_REMOVE_PRODUCT_MSG = "Successfully remove product";
    public static final String SUCCESS_CHECKOUT_MSG = "Successfully checkout";

    // Cart Error Message
    public static final String ERROR_CART_NOT_FOUND_MSG = "Cart not found";
    public static final String ERROR_STORE_ID_NOT_MATCH_MSG = "Store ID does not match";
    public static final String ERROR_ITEM_NOT_FOUND_IN_CART_MSG = "Item not found in cart";

    // Order Success Message
    public static final String SUCCESS_GET_ALL_ORDER_MSG = "Successfully get all order";
    public static final String SUCCESS_GET_ORDER_MSG = "Successfully get order";
    public static final String SUCCESS_CREATE_ORDER_MSG = "Successfully create order";
    public static final String SUCCESS_UPDATE_ORDER_MSG = "Successfully update order";
    public static final String SUCCESS_DELETE_ORDER_MSG = "Successfully delete order";

    // Order Error Message
    public static final String ERROR_ORDER_NOT_FOUND_MSG = "Order not found";

    // Payment Error Message
    public static final String ERROR_PAYMENT_GET_NOTIFICATION_MSG = "Invalid signature key";
}