package de.aittr.g_31_2_shop.domain.jpa;

import de.aittr.g_31_2_shop.domain.interfaces.Cart;
import de.aittr.g_31_2_shop.domain.interfaces.Customer;
import de.aittr.g_31_2_shop.domain.interfaces.Product;
import jakarta.persistence.*;

import java.time.temporal.ChronoUnit;
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

    @OneToOne
    @JoinColumn(name = "customer_id")
    private JpaCustomer customer;

    @ManyToMany // аннотация для List<Product> products
    @JoinTable(name = "cart_product", // название таблицы
            joinColumns = @JoinColumn(name = "cart_id"),  // название колонки в этой таблице, кот.привязана к
            // этому объекты
            inverseJoinColumns = @JoinColumn(name = "product_id") // название колонки, к которой привязан ниже
            // аннотированный лист
    )
    private List<JpaProduct> products = new ArrayList<>();

    public JpaCart() {
    }

    public JpaCart(int id, List<JpaProduct> products) {
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
        return new ArrayList<>(products); // надо вернуть <Product>, а у нас products это <JpaProduct>
    }

    public void setProducts(List<JpaProduct> products) {
        this.products = products;
    }

    public JpaCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(JpaCustomer customer) {
        this.customer = customer;
    }

    @Override
    public void addProduct(Product product) {
        try {
            products.add((JpaProduct) product);
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Incompatible Product type was passed to the JpaCart");
        }
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
        return products.stream().filter(p -> p.isActive()).mapToDouble(Product::getPrice).sum();
    }

    @Override
    public double getAveragePrice() {
        return products.stream().filter(p -> p.isActive()).mapToDouble(Product::getPrice).average().orElse(0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JpaCart jpaCart)) return false;
        return id == jpaCart.id && Objects.equals(customer, jpaCart.customer) && Objects.equals(products,
                jpaCart.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customer, products);
    }

    @Override
    public String toString() {
        return products.toString();
    }
}
