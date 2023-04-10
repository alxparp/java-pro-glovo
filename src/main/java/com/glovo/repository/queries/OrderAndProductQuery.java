package com.glovo.repository.queries;

public enum OrderAndProductQuery {

    SAVE_ORDER_PRODUCT("" +
           "INSERT INTO ORDER_PRODUCT (ORDER_ID, PRODUCT_ID) " +
           "VALUES (?, ?)");

    private String value;

    OrderAndProductQuery(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }



}
