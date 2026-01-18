package CRM.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import CRM.Entity.Support_entity;

@Repository
public interface Support_repository extends JpaRepository<Support_entity,Long>{

	public Support_entity findBySalesrepname(String Name);
	public List<Support_entity>findBySalesrepID(Long id); 
	public List<Support_entity>findAll();
	public Support_entity findByEmail(String email);
}
