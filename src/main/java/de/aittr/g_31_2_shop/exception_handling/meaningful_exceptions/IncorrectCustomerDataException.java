package de.aittr.g_31_2_shop.exception_handling.meaningful_exceptions;

public class IncorrectCustomerDataException extends RuntimeException {
    public IncorrectCustomerDataException(String message) {
        super(message);
    }
}
