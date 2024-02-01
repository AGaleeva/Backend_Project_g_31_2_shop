package de.aittr.g_31_2_shop.services.jdbc;

import de.aittr.g_31_2_shop.domain.dto.CustomerDto;
import de.aittr.g_31_2_shop.domain.interfaces.Customer;
import de.aittr.g_31_2_shop.repositories.interfaces.CustomerRepository;
import de.aittr.g_31_2_shop.services.interfaces.CustomerService;
import de.aittr.g_31_2_shop.services.mapping.CustomerMappingService;
import org.springframework.stereotype.Service;

import java.util.List;

//@Service
public class CommonCustomerService implements CustomerService {

    private CustomerRepository repository;
    private CustomerMappingService customerMappingService;

    public CommonCustomerService(CustomerRepository repository, CustomerMappingService customerMappingService) {
        this.repository = repository;
        this.customerMappingService = customerMappingService;
    }

    @Override
    public CustomerDto save(CustomerDto dto) {
        Customer customer = repository.save(customerMappingService.mapDtoToCommonCustomer(dto));
        return customerMappingService.mapCustomerEntityToDto(customer);
    }

    @Override
    public List<CustomerDto> getAllActiveCustomers() {
        return repository.getAll()
                .stream()
                .map(c -> customerMappingService.mapCustomerEntityToDto(c))
                .toList();
    }

    @Override
    public CustomerDto getActiveCustomerById(int id) {
        return null;
    }

    @Override
    public void update(CustomerDto customer) {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void deleteByName(String name) {

    }

    @Override
    public void restoreById(int id) {

    }

    @Override
    public int getActiveCustomerCount() {
        return 0;
    }

    @Override
    public double getCartTotalPriceById(int customerId) {
        return 0;
    }

    @Override
    public double getAvgProductPriceById(int customerId) {
        return 0;
    }

    @Override
    public void addProductToCart(int customerId, int productId) {

    }

    @Override
    public void deleteProductFromCart(int customerId, int productId) {

    }

    @Override
    public void clearCartById(int customerId) {

    }
}
