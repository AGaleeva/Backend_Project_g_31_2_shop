package de.aittr.g_31_2_shop.domain.jdbc;

import de.aittr.g_31_2_shop.domain.interfaces.Product;
import de.aittr.g_31_2_shop.enums.Status;

import java.util.Objects;

public class CommonProduct implements Product {

    private int id;
    private boolean isActive;
    private String name;
    private double price;
//    private Status status; // - передать в конструктор

    public CommonProduct() {
        isActive = true;
    }
    public CommonProduct(int id, boolean isActive, String name, double price) {
        this.id = id;
        this.isActive = isActive;
        this.name = name;
        this.price = price;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommonProduct that)) return false;
        return id == that.id && isActive == that.isActive && Double.compare(price, that.price) == 0 && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isActive, name, price);
    }

    @Override
    public String toString() {
        return String.format("id - %d, title - %s, price - %.2f, is active - %s.",
                id, name, price, isActive ? "yes" : "no");
        // %d - целочисленная переменная
        // %s - строковая переменная
        // %f - дробная переменная (.2 - количество знаков после запятой)
    }
}
