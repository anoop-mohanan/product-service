package com.myretail.product.exception;

public class ProductPriceNotFoundException extends RuntimeException {

    public ProductPriceNotFoundException(String message) {
        super(message);
    }
}
