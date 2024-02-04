package de.aittr.g_31_2_shop.exception_handling.meaningful_exceptions;

public class ProductNameNotFoundException extends RuntimeException {
    public ProductNameNotFoundException(String message) {
        super(message);
    }
}
