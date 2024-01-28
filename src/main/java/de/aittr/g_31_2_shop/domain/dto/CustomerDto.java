package de.aittr.g_31_2_shop.domain.dto;

import de.aittr.g_31_2_shop.domain.interfaces.Cart;

import java.util.Objects;

public class CustomerDto {

    private int id;
    private String name;
    private Cart cart;

    public CustomerDto(int id, String name, Cart cart) {
        this.id = id;
        this.name = name;
        this.cart = cart;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Cart getCart() {
        return cart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerDto that)) return false;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(cart, that.cart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, cart);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CustomerDto{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", cart=").append(cart);
        sb.append('}');
        return sb.toString();
    }
}
