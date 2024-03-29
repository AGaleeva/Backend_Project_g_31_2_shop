package de.aittr.g_31_2_shop.services.jpa;

import de.aittr.g_31_2_shop.domain.dto.CustomerDto;
import de.aittr.g_31_2_shop.domain.jpa.JpaCart;
import de.aittr.g_31_2_shop.domain.jpa.JpaCustomer;
import de.aittr.g_31_2_shop.exception_handling.meaningful_exceptions.IncorrectCustomerDataException;
import de.aittr.g_31_2_shop.repositories.jpa.JpaCustomerRepository;
import de.aittr.g_31_2_shop.services.interfaces.CustomerService;
import de.aittr.g_31_2_shop.services.mapping.CustomerMappingService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JpaCustomerService implements CustomerService {

    private JpaCustomerRepository repository;
    private CustomerMappingService mappingService;
    private Logger logger = LoggerFactory.getLogger(JpaCustomerService.class);

    public JpaCustomerService(JpaCustomerRepository repository, CustomerMappingService mappingService) {
        this.repository = repository;
        this.mappingService = mappingService;
    }

    @Override
    @Transactional
    public CustomerDto save(CustomerDto customerDto) {
        try {
            JpaCustomer entity = mappingService.mapDtoToJpaCustomer(customerDto);
            JpaCart cart = new JpaCart();
            entity.setCart(cart);
            cart.setCustomer(entity);
            entity = repository.save(entity);
            logger.info("The following values were set: {}, {}, {}, {}, {}, {}", entity.getId(), entity.getName(),
                    entity.getEmail(), entity.getAge(), entity.isActive(), entity.getCart());
            customerDto = mappingService.mapCustomerEntityToDto(entity);
            return customerDto;
        } catch (Exception e) {
            throw new IncorrectCustomerDataException(e.getMessage());
        }
    }

    @Override
    public List<CustomerDto> getAllActiveCustomers() {
        return repository.findAll()
                .stream()
                .filter(c -> c.isActive())
                .map(c -> mappingService.mapCustomerEntityToDto(c))
                .toList();
    }

    @Override
    public CustomerDto getActiveCustomerById(int id) {
        JpaCustomer entity = repository.findById(id).orElse(null);

        if (entity != null && entity.isActive()) {
            return mappingService.mapCustomerEntityToDto(entity);
        }
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
