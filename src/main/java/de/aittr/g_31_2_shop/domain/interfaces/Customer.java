package de.aittr.g_31_2_shop.domain.interfaces;

public interface Customer {

    int getId();

    void setId(int id);

    boolean isActive();

    void setActive(boolean isActive);

    String getName();

    void setName(String Name);

    String getEmail();

    void setEmail(String email);

    int getAge();

    void setAge(int age);

    Cart getCart();

    void setCart(Cart cart);
}
