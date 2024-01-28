package de.aittr.g_31_2_shop.controllers;

import de.aittr.g_31_2_shop.domain.jdbc.CommonCustomer;
import de.aittr.g_31_2_shop.domain.interfaces.Customer;
import de.aittr.g_31_2_shop.services.interfaces.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping
    public Customer save(@RequestBody CommonCustomer customer) {
        return service.save(customer);
    }

    @GetMapping
    public List<Customer> getAllActiveCustomers() {
        return service.getAllActiveCustomers();
    }
}
