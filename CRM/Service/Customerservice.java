package CRM.Service;

import java.util.List;

import CRM.Entity.Customer;

public interface Customerservice {
	List<Customer> getAllCustomers();
	Customer getCustomerById(Long id);
}
