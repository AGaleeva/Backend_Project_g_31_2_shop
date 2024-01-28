package de.aittr.g_31_2_shop.services.jdbc;

import de.aittr.g_31_2_shop.domain.interfaces.Customer;
import de.aittr.g_31_2_shop.repositories.interfaces.CustomerRepository;
import de.aittr.g_31_2_shop.services.interfaces.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommonCustomerService implements CustomerService {

    private CustomerRepository repository;

    public CommonCustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public Customer save(Customer customer) {
        return repository.save(customer);
    }

    @Override
    public List<Customer> getAllActiveCustomers() {
        return repository.getAll();
    }

    @Override
    public Customer getActiveCustomerById(int id) {
        return null;
    }

    @Override
    public void update(Customer customer) {

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
