package de.aittr.g_31_2_shop.exception_handling.meaningful_exceptions;

public class ZeroProductPriceException extends RuntimeException {
    public ZeroProductPriceException(String message) {
        super(message);
    }
}
