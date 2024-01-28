package de.aittr.g_31_2_shop.domain.jdbc;

import de.aittr.g_31_2_shop.domain.interfaces.Cart;
import de.aittr.g_31_2_shop.domain.interfaces.Customer;

import java.util.Objects;

public class CommonCustomer implements Customer {

    private int id;
    private boolean isActive;
    private String name;
    private Cart cart;

    public CommonCustomer() {
        this.isActive = true;
    }

    public CommonCustomer(int id, boolean isActive, String name, Cart cart) {
        this.id = id;
        this.isActive = isActive;
        this.name = name;
        this.cart = cart;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Cart getCart() {
        return cart;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommonCustomer that)) return false;
        return id == that.id && isActive == that.isActive && Objects.equals(name, that.name) && Objects.equals(cart, that.cart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isActive, name, cart);
    }

    @Override
    public String toString() {
        return String.format("Customer: id - %d, name - %s, is active - %s, cart content: %n%s", id, name, isActive ?
                "yes" : "no", cart);
    }
}
