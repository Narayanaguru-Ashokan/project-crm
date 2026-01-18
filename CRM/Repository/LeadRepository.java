package CRM.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import CRM.Entity.Lead;

@Repository
public interface LeadRepository extends JpaRepository<Lead, Long> {

	List<Lead> findByLeadStatus(String string);

	List<Lead> findBySourceIgnoreCase(String source);

	Lead findFirstByLeadStatusOrderByIdAsc(String status);
	
	
	
	@Query("SELECT lead.email FROM Lead lead")
	List<String> getAllEmails();


}
