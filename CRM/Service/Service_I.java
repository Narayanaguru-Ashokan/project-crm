package CRM.Service;

import CRM.Entity.Lead;
import CRM.Entity.Service_entity;

@org.springframework.stereotype.Service
public interface Service_I {
	Lead updateLeadStatus(Long leadId, String status);

	Service_entity createService(Service_entity serviceEntity);

	String deleteService(Long id);
	
	Service_entity findByEmail(String email);

}
 
