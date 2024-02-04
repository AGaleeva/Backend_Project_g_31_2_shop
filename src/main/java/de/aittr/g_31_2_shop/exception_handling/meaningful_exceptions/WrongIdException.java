package de.aittr.g_31_2_shop.exception_handling.meaningful_exceptions;

public class WrongIdException extends RuntimeException {

    private final long timestamp;

    public WrongIdException(String message) {
        super(message);
        this.timestamp = System.currentTimeMillis();
    }

    public long getTimestamp() {
        return timestamp;
    }
}
