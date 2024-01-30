package de.aittr.g_31_2_shop.services.mapping;

import de.aittr.g_31_2_shop.domain.dto.CustomerDto;
import de.aittr.g_31_2_shop.domain.interfaces.Cart;
import de.aittr.g_31_2_shop.domain.interfaces.Customer;
import de.aittr.g_31_2_shop.domain.jdbc.CommonCustomer;
import de.aittr.g_31_2_shop.domain.jpa.JpaCart;
import de.aittr.g_31_2_shop.domain.jpa.JpaCustomer;
import org.springframework.stereotype.Service;

@Service
public class CustomerMappingService {

    private CartMappingService cartMappingService;

    public CustomerMappingService(CartMappingService cartMappingService) {
        this.cartMappingService = cartMappingService;
    }

    public CustomerDto mapCustomerEntityToDto(Customer customer) {
        int id = customer.getId();
        String name = customer.getName();
        Cart cart = customer.getCart();
        return new CustomerDto(id, name, cartMappingService.mapCartEntityToDto(cart));
    }

    public JpaCustomer mapDtoToJpaCustomer(CustomerDto customerDto) {
        int id = customerDto.getId();
        String name = customerDto.getName();
        JpaCart cart = cartMappingService.mapDtoToJpaCart(customerDto.getCart());
        return new JpaCustomer(id, name, true, cart);
    }

    public CommonCustomer mapDtoToCommonCustomer(CustomerDto customerDto) {
        int id = customerDto.getId();
        String name = customerDto.getName();
        Cart cart = cartMappingService.mapDtoToCommonCart(customerDto.getCart());
        return new CommonCustomer(id, true, name, cart);
    }
}
