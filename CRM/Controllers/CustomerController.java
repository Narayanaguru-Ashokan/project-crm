package CRM.Controllers;

import CRM.Entity.Customer;
import CRM.Repository.CustomerRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@CrossOrigin(origins = "*")
public class CustomerController {

    private final CustomerRepository repo;

    public CustomerController(CustomerRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return repo.findAll();
    }
}

