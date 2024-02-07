package de.aittr.g_31_2_shop.domain.jpa;

import de.aittr.g_31_2_shop.domain.interfaces.Cart;
import de.aittr.g_31_2_shop.domain.interfaces.Customer;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

@Entity
@Table(name = "customer")
public class JpaCustomer implements Customer {

    private static Logger logger = LoggerFactory.getLogger(JpaProduct.class);



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    @Email
    private String email;

    @Column(name = "age")
    @Min(18)
    private int age;

    @Column(name = "is_active")
    private boolean isActive;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL) // указывается название поля с связанном классе JpaCart
    private JpaCart cart;               //  (поле private Customer customer;)


    public JpaCustomer() {
    }

    public JpaCustomer(int id, String name, String email, int age, boolean isActive, JpaCart cart) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.isActive = isActive;
        this.cart = cart;
        logger.info("Constructor of the class JpaCustomer called with fields id, name, email, isActive, cart");
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
        try {
            this.cart = (JpaCart) cart;
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Incompatible Cart type was passed to the setter");
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JpaCustomer that)) return false;
        return id == that.id && isActive == that.isActive && Objects.equals(name, that.name) && Objects.equals(cart,
                that.cart);
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
