package de.aittr.g_31_2_shop.domain.jpa;

import de.aittr.g_31_2_shop.domain.interfaces.Cart;
import de.aittr.g_31_2_shop.domain.interfaces.Customer;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "customer")
public class JpaCustomer implements Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "is_active")
    private boolean isActive;

    private Cart cart;

    public JpaCustomer() {
    }

    public JpaCustomer(int id, String name, boolean isActive, Cart cart) {
        this.id = id;
        this.name = name;
        this.isActive = isActive;
        this.cart = cart;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Cart getCart() {
        return cart;
    }

    @Override
    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JpaCustomer that)) return false;
        return id == that.id && isActive == that.isActive && Objects.equals(name, that.name) && Objects.equals(cart, that.cart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, isActive, cart);
    }

    @Override
    public String toString() {
        return String.format("Customer: id - %d, name - %s, is active - %s, cart content: %n%s", id, name, isActive ?
                "yes" : "no", cart);
    }
}
