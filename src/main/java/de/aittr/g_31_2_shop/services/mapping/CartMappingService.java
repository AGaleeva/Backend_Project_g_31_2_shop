package de.aittr.g_31_2_shop.services.mapping;

import de.aittr.g_31_2_shop.domain.dto.CartDto;
import de.aittr.g_31_2_shop.domain.dto.CustomerDto;
import de.aittr.g_31_2_shop.domain.interfaces.Cart;
import de.aittr.g_31_2_shop.domain.interfaces.Customer;
import de.aittr.g_31_2_shop.domain.interfaces.Product;
import de.aittr.g_31_2_shop.domain.jpa.JpaCart;
import de.aittr.g_31_2_shop.domain.jpa.JpaCustomer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartMappingService {

    public CartDto mapCartEntityToDto(Cart cart) {
        int id = cart.getId();
        List<Product> products = cart.getProducts();
        return new CartDto(id, products);
    }

    public JpaCart mapDtoToJpaCart(CartDto cartDto) {
        int id = cartDto.getId();
        List<Product> products = cartDto.getProducts();
        return new JpaCart(id, products);
    }
}
