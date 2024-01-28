package de.aittr.g_31_2_shop.domain.jpa;

import de.aittr.g_31_2_shop.domain.interfaces.Cart;
import de.aittr.g_31_2_shop.domain.interfaces.Product;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "cart")
public class JpaCart implements Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "customer_id")
    private int customerId;

    private List<Product> products = new ArrayList<>();

    public JpaCart() {
    }

    public JpaCart(int id, List<Product> products) {
        this.id = id;
        this.products = products;
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
    public List<Product> getProducts() {
        return products;
    }

    @Override
    public void addProduct(Product product) {
        products.add(product);
    }

    @Override
    public void deleteProductById(int productId) {
        products.removeIf(p -> p.getId() == productId);
    }

    @Override
    public void clear() {
        products.clear();
    }

    @Override
    public double getTotalPrice() {
        return products.stream()
                .filter(p -> p.isActive())
                .mapToDouble(Product::getPrice)
                .sum();
    }

    @Override
    public double getAveragePrice() {
        return products.stream()
                .filter( p-> p.isActive())
                .mapToDouble(Product::getPrice)
                .average()
                .orElse(0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JpaCart jpaCart)) return false;
        return id == jpaCart.id && customerId == jpaCart.customerId && Objects.equals(products, jpaCart.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId, products);
    }

    @Override
    public String toString() {
        return products.toString();
    }
}
