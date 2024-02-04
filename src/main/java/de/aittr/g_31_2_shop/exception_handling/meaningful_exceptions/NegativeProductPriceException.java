package de.aittr.g_31_2_shop.exception_handling.meaningful_exceptions;

public class NegativeProductPriceException extends RuntimeException {
    public NegativeProductPriceException(String message) {
        super(message);
    }
}
