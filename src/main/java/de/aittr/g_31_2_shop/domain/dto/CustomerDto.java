package de.aittr.g_31_2_shop.domain.dto;

import de.aittr.g_31_2_shop.domain.interfaces.Cart;

import java.util.Objects;

public class CustomerDto {

    private int id;
    private String name;
    private String email;
    private int age;
    private CartDto cart;

    public CustomerDto(int id, String name, String email, int age, CartDto cart) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.cart = cart;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public CartDto getCart() {
        return cart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerDto that)) return false;
        return id == that.id && age == that.age && Objects.equals(name, that.name) && Objects.equals(email, that.email) && Objects.equals(cart, that.cart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, age, cart);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CustomerDto{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", age=").append(age);
        sb.append(", cart=").append(cart);
        sb.append('}');
        return sb.toString();
    }
}
