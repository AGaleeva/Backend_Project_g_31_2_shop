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
        String email = customer.getEmail();
        int age = customer.getAge();
        Cart cart = customer.getCart();
        return new CustomerDto(id, name, email, age, cartMappingService.mapCartEntityToDto(cart));
    }

    public JpaCustomer mapDtoToJpaCustomer(CustomerDto customerDto) {
//        int id = customerDto.getId();
        String name = customerDto.getName();
        String email = customerDto.getEmail();
        int age = customerDto.getAge();
//        JpaCart cart = cartMappingService.mapDtoToJpaCart(customerDto.getCart()); // Корзина будет создаваться и
                                                                  // сетится в поле покупателя при сохранении последнего
        return new JpaCustomer(0, name, email, age, true, null);
    }

    public CommonCustomer mapDtoToCommonCustomer(CustomerDto customerDto) {
        int id = customerDto.getId();
        String name = customerDto.getName();
        String email = customerDto.getEmail();
        int age = customerDto.getAge();
        Cart cart = cartMappingService.mapDtoToCommonCart(customerDto.getCart());
        return new CommonCustomer(id, true, name, email, age, cart);
    }
}
