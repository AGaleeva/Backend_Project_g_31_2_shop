package de.aittr.g_31_2_shop.exception_handling.meaningful_exceptions;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
