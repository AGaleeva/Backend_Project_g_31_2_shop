package de.aittr.g_31_2_shop.exception_handling;

public class ValidationResponse extends Response {

    private String cause;

    public ValidationResponse(String message, String cause) {
        super(message);
        this.cause = cause;
    }

    public String getCause() {
        return cause;
    }
}
