package de.aittr.g_31_2_shop.domain.jpa;

import de.aittr.g_31_2_shop.domain.interfaces.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

@Entity
@Table(name = "product")
public class JpaProduct implements Product {

    private static Logger logger = LoggerFactory.getLogger(JpaProduct.class);

    // Pear - OK
    // pear - x
    // PEAR - x
    // PEar - x
    // Pe - x
    // Pear7 - x
    // Pear# - x
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
//    @NotNull
//    @NotBlank
    @Pattern(regexp = "^[A-Z][a-z]{3,}(_[A-Za-z][a-z]*)?$")
    private String name;

    @Column(name = "price")
    @Min(10)
    @Max(10000)
    private double price;

    @Column(name = "is_active")
    private boolean isActive;

    public JpaProduct() {
    }

    public JpaProduct(int id, boolean isActive, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.isActive = isActive;
        logger.info("Constructor of the class JpaProduct was called with fields 'id', 'name', 'price', 'isActive'");
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
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JpaProduct that)) return false;
        return id == that.id && Double.compare(price, that.price) == 0 && isActive == that.isActive && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, isActive);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("JpaProduct{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", price=").append(price);
        sb.append(", isActive=").append(isActive);
        sb.append('}');
        return sb.toString();
    }
}
