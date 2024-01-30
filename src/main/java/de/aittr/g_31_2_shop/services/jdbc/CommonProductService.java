package de.aittr.g_31_2_shop.services.jdbc;

import de.aittr.g_31_2_shop.domain.jdbc.CommonProduct;
import de.aittr.g_31_2_shop.domain.dto.ProductDto;
import de.aittr.g_31_2_shop.domain.interfaces.Product;
import de.aittr.g_31_2_shop.enums.Status;
import de.aittr.g_31_2_shop.repositories.interfaces.ProductRepository;
import de.aittr.g_31_2_shop.services.interfaces.ProductService;
import de.aittr.g_31_2_shop.services.mapping.ProductMappingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
// отключен, чтобы создать только один бин сервиса для передачи контроллеру - JpaProductService
public class CommonProductService implements ProductService {

    private ProductRepository repository;
    private ProductMappingService mappingService;

    public CommonProductService(ProductRepository repository, ProductMappingService mappingService) {
        this.repository = repository;
        this.mappingService = mappingService;
    }

    @Override
    public ProductDto save(ProductDto product) {
        CommonProduct commonProduct = mappingService.mapDtoToCommonProduct(product);
        Product savedProduct = repository.save(commonProduct);
        return mappingService.mapProductEntityToDto(savedProduct);
    }

    @Override
    public List<ProductDto> getAllActiveProducts() {
        return  repository.getAll()
                    .stream()
                    .map(p -> mappingService.mapProductEntityToDto(p))
                    .toList();
    }

    @Override
    public ProductDto getActiveProductById(int id) {
        return null;
    }

    @Override
    public void update(ProductDto dto) {
        repository.update(mappingService.mapDtoToCommonProduct(dto));
    }

    /*@Override
    public void deleteById(int id) {
        repository.deleteById(id);
    }*/

    @Override
    public void deleteById(int id) {
        repository.changeStatusById(id, Status.DELETE);
    }

    @Override
    public void deleteByName(String name) {

    }

    @Override
    public void restoreById(int id) {
        repository.changeStatusById(id, Status.RESTORE);
    }

    @Override
    public int getActiveProductCount() {
        return 0;
    }

    @Override
    public double getActiveProductTotalPrice() {
        return 0;
    }

    @Override
    public double getActiveProductAvgPrice() {
        return 0;
    }
}
