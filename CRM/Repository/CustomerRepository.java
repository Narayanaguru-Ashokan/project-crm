package CRM.Repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import CRM.Entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	@Query("SELECT customer.email FROM Customer customer")
	List<String> getAllEmails();

}

