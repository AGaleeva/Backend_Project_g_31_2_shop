package de.aittr.g_31_2_shop.services.jpa;

import de.aittr.g_31_2_shop.domain.dto.ProductDto;
import de.aittr.g_31_2_shop.domain.interfaces.Product;
import de.aittr.g_31_2_shop.domain.jpa.JpaProduct;
import de.aittr.g_31_2_shop.domain.jpa.Task;
import de.aittr.g_31_2_shop.exception_handling.meaningful_exceptions.*;
import de.aittr.g_31_2_shop.exception_handling.meaningless_exceptions.ThirdTestException;
import de.aittr.g_31_2_shop.repositories.jpa.JpaProductRepository;
import de.aittr.g_31_2_shop.scheduling.ScheduleExecutor;
import de.aittr.g_31_2_shop.scheduling.SchedulerHwTasks;
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

//            Task task = new Task("The last saved product is %s".formatted(entity.getName()));
//            SchedulerHwTasks.scheduleAndExecuteTask(task);

            logger.info("The following values were set: {}, {}, {}, {}", entity.getId(), entity.getName(),
                    entity.getPrice(), entity.isActive());
            return mappingService.mapProductEntityToDto(entity);
        } catch (Exception e) {
//            throw new FourthTestException(e.getMessage());
            throw new ProductValidationException("Incorrect values of product fields.", e);
        }
    }

    @Override
    public List<ProductDto> getAllActiveProducts() {
        Task task = new Task("The method getAllActiveProducts() called");
//        ScheduleExecutor.scheduleAndExecuteTask(task); // для запуска задачи по расписанию в кл. ScheduleExecutor

//        Here will be placed JointPoint (helper code).
        return repository.findAll().stream().filter(JpaProduct::isActive)  // p -> p.isActive()
                .map(p -> mappingService.mapProductEntityToDto(p)).toList();
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
        } else {
            throw new WrongIdException("There is no product with the provided ID in the Database");
        }
    }

    @Override
    @Transactional
    public void deleteByName(String name) {
        Product product = repository.findByName(name);

        if (product != null && product.isActive()) {
            product.setActive(false);
        } else {
            throw new ProductNameNotFoundException("There is no product with the name provided in the Database, or " + "the name is empty");
        }
    }

    @Override
    @Transactional
    public void restoreById(int id) {
        Product product = repository.findById(id).orElse(null);

        if (product != null && !product.isActive()) {
            product.setActive(true);
        } else {
            throw new WrongIdException("There is no product with the provided ID in the Database");
        }
    }

    @Override
    public int getActiveProductCount() {
        return (int) repository.findAll().stream().filter(p -> p.isActive()).count();
    }

    @Override
    public double getActiveProductTotalPrice() {
        return repository.findAll().stream()
                .filter(p -> p != null && p.isActive())
                .mapToDouble(p -> {
                    if (p.getPrice() == 0) {
                        throw new ZeroProductPriceException("The product price cannot be zero");
                    }
                    return p.getPrice();
                })
                .sum();
//        return repository.findAll().stream().filter(p -> p.isActive()).mapToDouble(p -> p.getPrice()).sum();
    }

    @Override
    public double getActiveProductAvgPrice() {
        return repository.findAll().stream()
                .filter(p -> p.isActive()).mapToDouble(p -> {
            if (p.getPrice() < 0) {
                throw new NegativeProductPriceException("The product price cannot be negative");
            }
            return p.getPrice();
        })
                .average()
                .orElse(0);

//        return repository.findAll().stream().filter(p -> p.isActive()).mapToDouble(p -> p.getPrice()).average().orElse(0);
    }
}
