package de.aittr.g_31_2_shop.services.mapping;

import de.aittr.g_31_2_shop.domain.dto.CustomerDto;
import de.aittr.g_31_2_shop.domain.interfaces.Cart;
import de.aittr.g_31_2_shop.domain.interfaces.Customer;
import de.aittr.g_31_2_shop.domain.jpa.JpaCustomer;
import org.springframework.stereotype.Service;

@Service
public class CustomerMappingService {

    public CustomerDto mapCustomerEntityToDto(Customer customer) {
        int id = customer.getId();
        String name = customer.getName();
        Cart cart = customer.getCart();
        return new CustomerDto(id, name, cart);
    }

    public JpaCustomer mapDtoToJpaCustomer(CustomerDto customerDto) {
        int id = customerDto.getId();
        String name = customerDto.getName();
        Cart cart = customerDto.getCart();
        return new JpaCustomer(id, name, true, cart);
    }
}
