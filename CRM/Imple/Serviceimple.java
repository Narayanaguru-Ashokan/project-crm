package CRM.Imple;

import CRM.Repository.LeadRepository;
import CRM.Repository.ServiceRepository; // Assuming ServiceRepository exists
import CRM.Service.Lead_service;
import CRM.Service.Service_I;
import CRM.Entity.Lead;
import CRM.Entity.Service_entity;
import CRM.Exception.LeadNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class Serviceimple implements Service_I {

	private static final Logger logger = LoggerFactory.getLogger(Serviceimple.class);

	@Autowired
	private LeadRepository leadRepository;

	@Autowired
	private ServiceRepository serviceRepository;

	@Autowired
	private Lead_service Leadservice;

	@Transactional
	public Lead updateLeadStatus(Long leadId, String status) {

	    Lead lead = leadRepository.findById(leadId)
	            .orElseThrow(() -> new LeadNotFoundException("Lead not found with ID: " + leadId));

	    if (!lead.getLeadStatus().equalsIgnoreCase("new")) {
	        throw new IllegalArgumentException("Only NEW leads can be updated by service team.");
	    }


	    lead.setLeadStatus(status);

	    if (status.equalsIgnoreCase("positive")) {
	        Leadservice.convertLeadToCustomer(lead);
	        return lead;
	    }
	    
	    if (status.equalsIgnoreCase("negative")) {
	        lead.setLeadStatus("negative");
	        Lead saved = leadRepository.save(lead);
	        logger.info("Lead ID={} updated to NEGATIVE by Service Team", leadId);
	        return saved;
	    }
	    
	    throw new RuntimeException("Invalid status. Use positive or negative");
	}
	@Override
	public Service_entity findByEmail(String email) {
	    return serviceRepository.findByEmail(email);
	}


	public Service_entity createService(Service_entity serviceEntity) {
		logger.info("Creating new service entity with seller name: {}", serviceEntity.getSeller_name());

		try {
			Service_entity createdService = serviceRepository.save(serviceEntity);
			logger.info("Service entity created with ID: {}", createdService.getId());
			return createdService;
		} catch (Exception e) {
			logger.error("Error creating service entity. Error: {}", e.getMessage());
			throw e;
		}
	}

	public String deleteService(Long id) {
		logger.info("Attempting to delete service entity with ID: {}", id);

		try {
			Service_entity service = serviceRepository.findById(id)
					.orElseThrow(() -> new RuntimeException("Service entity not found with ID: " + id));

			serviceRepository.delete(service);
			logger.info("Service entity with ID: {} has been deleted", id);
			return "Service entity deleted successfully";
		} catch (RuntimeException e) {
			logger.error("Error deleting service entity with ID: {}. Error: {}", id, e.getMessage());
			throw e;
		}
	}

	
}
