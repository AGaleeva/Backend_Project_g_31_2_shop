package de.aittr.g_31_2_shop.services.jpa;

import de.aittr.g_31_2_shop.domain.dto.ProductDto;
import de.aittr.g_31_2_shop.domain.interfaces.Product;
import de.aittr.g_31_2_shop.domain.jpa.JpaProduct;
import de.aittr.g_31_2_shop.repositories.jpa.JpaProductRepository;
import de.aittr.g_31_2_shop.services.interfaces.ProductService;
import de.aittr.g_31_2_shop.services.mapping.ProductMappingService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JpaProductService implements ProductService {

    private JpaProductRepository repository;
    private ProductMappingService mappingService;

    public JpaProductService(JpaProductRepository repository, ProductMappingService mappingService) {
        this.repository = repository;
        this.mappingService = mappingService;
    }

    @Override
    public ProductDto save(ProductDto dto) {
        JpaProduct entity = mappingService.mapDtoToJpaProduct(dto);
        entity.setId(0);
        entity = repository.save(entity);
        return mappingService.mapProductEntityToDto(entity);
    }

    @Override
    public List<ProductDto> getAllActiveProducts() {
        return repository.findAll().stream()
                .filter(JpaProduct::isActive)  // p -> p.isActive()
                .map(p -> mappingService.mapProductEntityToDto(p))
                .toList();
    }

    @Override
    public ProductDto getActiveProductById(int id) {
        JpaProduct product = repository.findById(id).orElse(null);
//        product = product.isActive() ? product : null;
//        return mappingService.mapProductEntityToDto(product);
        if (product != null && product.isActive()) {
            return mappingService.mapProductEntityToDto(product);
        }
        return null;
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
