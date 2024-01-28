package de.aittr.g_31_2_shop.enums;

public enum Status {

    DELETE(0),
    RESTORE(1),
    ARCHIVE(2);

    private int value;

    Status(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
