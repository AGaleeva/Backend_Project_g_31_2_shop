package de.aittr.g_31_2_shop.exception_handling.meaningful_exceptions;

public class ProductEmptyNameException extends RuntimeException {
    public ProductEmptyNameException(String message) {
        super(message);
    }
}
