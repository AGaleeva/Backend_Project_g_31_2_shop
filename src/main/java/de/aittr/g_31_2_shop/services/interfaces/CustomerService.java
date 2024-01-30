package de.aittr.g_31_2_shop.services.interfaces;

import de.aittr.g_31_2_shop.domain.dto.CustomerDto;

import java.util.List;

public interface CustomerService {

    CustomerDto save(CustomerDto customer);

    List<CustomerDto> getAllActiveCustomers();

    CustomerDto getActiveCustomerById(int id);

    void update(CustomerDto customer);

    void  deleteById(int id);

    void deleteByName(String name);

    void  restoreById(int id);

    int getActiveCustomerCount();

    double getCartTotalPriceById(int customerId);

    double getAvgProductPriceById(int customerId);

    void addProductToCart(int customerId, int productId);

    void deleteProductFromCart(int customerId, int productId);

    void clearCartById(int customerId);
}
