package CRM.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import CRM.Entity.Service_entity;

public interface ServiceRepository extends JpaRepository<Service_entity, Long> {
	 public Service_entity findByEmail(String email);
}
