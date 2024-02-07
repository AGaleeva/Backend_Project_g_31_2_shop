package de.aittr.g_31_2_shop.exception_handling.meaningful_exceptions;

public class ProductValidationException extends RuntimeException {

    public ProductValidationException(String message) {
        super(message);
    }

    public ProductValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
