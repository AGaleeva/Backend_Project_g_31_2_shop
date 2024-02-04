package de.aittr.g_31_2_shop.services.jpa;

import de.aittr.g_31_2_shop.domain.dto.ProductDto;
import de.aittr.g_31_2_shop.domain.interfaces.Product;
import de.aittr.g_31_2_shop.domain.jpa.JpaProduct;
import de.aittr.g_31_2_shop.exception_handling.meaningless_exceptions.FourthTestException;
import de.aittr.g_31_2_shop.exception_handling.meaningless_exceptions.ThirdTestException;
import de.aittr.g_31_2_shop.repositories.jpa.JpaProductRepository;
import de.aittr.g_31_2_shop.services.interfaces.ProductService;
import de.aittr.g_31_2_shop.services.mapping.ProductMappingService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JpaProductService implements ProductService {

    private JpaProductRepository repository;
    private ProductMappingService mappingService;
//    private Logger logger = LogManager.getLogger(JpaProductService.class); // log4j-library
    private Logger logger = LoggerFactory.getLogger(JpaProductService.class); // org.slf4j-library

    public JpaProductService(JpaProductRepository repository, ProductMappingService mappingService) {
        this.repository = repository;
        this.mappingService = mappingService;
    }

    @Override
    public ProductDto save(ProductDto dto) {
        try {
            JpaProduct entity = mappingService.mapDtoToJpaProduct(dto);
            entity.setId(0);
            entity = repository.save(entity);
            return mappingService.mapProductEntityToDto(entity);
        } catch (Exception e) {
            throw new FourthTestException(e.getMessage());
        }
    }

    @Override
    public List<ProductDto> getAllActiveProducts() {
//        Here will be placed JointPoint (helper code).
        return repository.findAll().stream()
                .filter(JpaProduct::isActive)  // p -> p.isActive()
                .map(p -> mappingService.mapProductEntityToDto(p))
                .toList();
    }

    @Override
    public ProductDto getActiveProductById(int id) {

//        Can be used only with log4j-library
//        logger.log(Level.INFO, String.format("A product with id %d was requested", id));
//        logger.log(Level.WARN, String.format("A product with id %d was requested", id));
//        logger.log(Level.ERROR, String.format("A product with id %d was requested", id));

//        Can be used with both libraries log4j-library and org.slf4j-library
//        logger.info(String.format("A product with id %d was requested", id));
//        logger.warn(String.format("A product with id %d was requested", id));
//        logger.error(String.format("A product with id %d was requested", id));

        Product product = repository.findById(id).orElse(null);
//        product = product.isActive() ? product : null;
//        return mappingService.mapProductEntityToDto(product);
        if (product != null && product.isActive()) {
            return mappingService.mapProductEntityToDto(product);
        }
//        return null;  // для 1 способа обработки эксцепшенов, когда кэтчер пишется в контроллере

//        throw new FirstTestException("There is no product with the provided ID in the Database");
//        throw new SecondTestException("There is no product with the provided ID in the Database");
        throw new ThirdTestException("There is no product with the provided ID in the Database");
    }

    @Override
    public void update(ProductDto productDto) {
        JpaProduct entity = mappingService.mapDtoToJpaProduct(productDto);
        repository.save(entity);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        Product product = repository.findById(id).orElse(null);

        if (product != null && product.isActive()) {
            product.setActive(false);
        }
    }

    @Override
    @Transactional
    public void deleteByName(String name) {
        Product product = repository.findByName(name);

        if (product != null && product.isActive()) {
            product.setActive(false);
        }
    }

    @Override
    @Transactional
    public void restoreById(int id) {
        Product product = repository.findById(id).orElse(null);

        if (product != null && !product.isActive()) {
            product.setActive(true);
        }
    }

    @Override
    public int getActiveProductCount() {
        return (int) repository.findAll()
                        .stream()
                        .filter(p -> p.isActive())
                        .count();
    }

    @Override
    public double getActiveProductTotalPrice() {
        return repository.findAll()
                .stream()
                .filter(p -> p.isActive())
                .mapToDouble(p -> p.getPrice())
                .sum();
    }

    @Override
    public double getActiveProductAvgPrice() {
        return repository.findAll()
                .stream()
                .filter(p -> p.isActive())
                .mapToDouble(p -> p.getPrice())
                .average().orElse(0);
    }
}
