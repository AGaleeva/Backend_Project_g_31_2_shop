package de.aittr.g_31_2_shop.services.jpa;

import de.aittr.g_31_2_shop.domain.dto.ProductDto;
import de.aittr.g_31_2_shop.domain.interfaces.Product;
import de.aittr.g_31_2_shop.domain.jpa.JpaProduct;
import de.aittr.g_31_2_shop.repositories.jpa.JpaProductRepository;
import de.aittr.g_31_2_shop.services.interfaces.ProductService;
import de.aittr.g_31_2_shop.services.mapping.ProductMappingService;
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
        List<JpaProduct> products = repository.findAll();
        List<ProductDto> productDtos = products.stream()
                .filter(p -> p.isActive())
                .map(p -> mappingService.mapProductEntityToDto(p))
                .toList();
        return productDtos;
    }

    @Override
    public ProductDto getActiveProductById(int id) {
        JpaProduct product = repository.findById(id).orElse(null);
        product = product.isActive() ? product : null;
        return mappingService.mapProductEntityToDto(product);
    }

    @Override
    public void update(ProductDto productDto) {
        JpaProduct entity = mappingService.mapDtoToJpaProduct(productDto);
        entity.setId(productDto.getId());
        repository.save(entity);
    }

    @Override
    public void deleteById(int id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteByName(String name) {
        repository.deleteByName(name);
    }

    @Override
    public void restoreById(int id) {
        JpaProduct product = repository.findById(id).orElse(null);
        assert product != null;
        product.setActive(true);
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
