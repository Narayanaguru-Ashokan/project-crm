package CRM.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import CRM.Entity.ClosedLead;

public interface ClosedLeadRepository extends JpaRepository<ClosedLead, Long> {
	@Query("SELECT lead.email FROM ClosedLead lead")
	List<String> getAllEmails();

}
