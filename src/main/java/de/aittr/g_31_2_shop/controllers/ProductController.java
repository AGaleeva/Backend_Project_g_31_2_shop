package de.aittr.g_31_2_shop.controllers;

import de.aittr.g_31_2_shop.domain.dto.ProductDto;
import de.aittr.g_31_2_shop.exception_handling.Response;
import de.aittr.g_31_2_shop.exception_handling.meaningless_exceptions.FirstTestException;
import de.aittr.g_31_2_shop.services.interfaces.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public ProductDto save(@Valid @RequestBody ProductDto product) {
        return service.save(product);
    }

    @GetMapping
    public List<ProductDto> getAll() {
        return service.getAllActiveProducts();
    }

    @GetMapping("/{id}") // original variant
    public ProductDto getById(@PathVariable int id) {
        return service.getActiveProductById(id);
    }

    // 1 способ, см. ниже, но лучше все-таки ошибку выбрасывать в сервисе (FirstTestException)
    /*@GetMapping("/{id}")
    public ProductDto getById(@PathVariable int id) {
        ProductDto dto = service.getActiveProductById(id);

        if (dto == null) {
            throw new FirstTestException("There is no product with the provided ID in the Database");
        }

        return dto;
    }*/

    @PutMapping
    public void update(@RequestBody ProductDto dto) {
        service.update(dto);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) {
        service.deleteById(id);
    }

    @DeleteMapping("/del_by_name/{name}")
    public void deleteByName(@PathVariable String name) {
        service.deleteByName(name);
    }

    @PutMapping("/{id}")
    public void restoreById(@PathVariable int id) {
        service.restoreById(id);
    }

    @GetMapping("/count")
    public int getActiveProductCount() {
        return service.getActiveProductCount();
    }

    @GetMapping("/total_price")
    public double getActiveProductTotalPrice() {
        return service.getActiveProductTotalPrice();
    }

    @GetMapping("/avg_price")
    public double getActiveProductAvgPrice() {
        return service.getActiveProductAvgPrice();
    }

    // 1 способ - создание метода-обработчика в контроллере, где мы ожидаем ошибки
    // Минус - если в разных контроллерах требуется обрабатывать ошибки одинаково, то придется написать один и тот же
    // код в разных контроллерах
    // Плюс - а вот если надо обрабатывать ошибки по-разному, то мы можем легко это сделать
    @ExceptionHandler(FirstTestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response handleException(FirstTestException e) {
        return new Response(e.getMessage());
    }
}
