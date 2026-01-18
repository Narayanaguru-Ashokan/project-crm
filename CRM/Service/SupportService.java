package CRM.Service;

import java.util.List;

import CRM.Entity.Lead;
import CRM.Entity.Support_entity;

public interface SupportService {

	List<Lead> getNegativeLeads();

	Object handleLeadAction(Long id, Integer action, String reason);

	List<Support_entity> findAll();

	Support_entity createSupport(Support_entity supportEntity);

	String deleteSupport(Long salesrepID);
	
	Support_entity findByEmail(String email);
	

}
