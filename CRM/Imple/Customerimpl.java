package CRM.Imple;

import java.util.List;
import org.springframework.stereotype.Service;
import CRM.Entity.Customer;
import CRM.Repository.CustomerRepository;

@Service
public class Customerimpl {

	private final CustomerRepository CustomerRepository;

	public Customerimpl(CustomerRepository CustomerRepository) {
		this.CustomerRepository = CustomerRepository;
	}

	public List<Customer> getAllCustomers() {
		return CustomerRepository.findAll();
	}

	public Customer getCustomerById(Long id) {
		return CustomerRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
	}
}
