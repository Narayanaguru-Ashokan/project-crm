package CRM.Imple;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import CRM.Exception.LeadNotFoundException;
import CRM.Entity.ClosedLead;
import CRM.Entity.Lead;
import CRM.Entity.Support_entity;
import CRM.Repository.ClosedLeadRepository;
import CRM.Repository.LeadRepository;
import CRM.Repository.Support_repository;
import CRM.Service.Lead_service;
import CRM.Service.SupportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class SupportImpl implements SupportService {

	private static final Logger logger = LoggerFactory.getLogger(SupportImpl.class);

	@Autowired
	private LeadRepository leadRepo;

	@Autowired
	private ClosedLeadRepository closedLeadRepo;

	@Autowired
	private Support_repository S_repo;
	
	@Autowired
    private Lead_service leadService;

	@Override
	public List<Lead> getNegativeLeads() {
		logger.info("Fetching all leads with 'negative' status.");
		List<Lead> negativeLeads = leadRepo.findByLeadStatus("negative");
		logger.info("Found {} negative leads.", negativeLeads.size());
		return negativeLeads;
	}

	  public Object handleLeadAction(Long id, Integer action, String reason) {

	        logger.info("Support Team action received: Lead ID={}, Action={}, Reason={}", id, action, reason);

	        Lead lead = leadRepo.findById(id).orElseThrow(() -> {
	            logger.error("Lead not found during support action, ID={}", id);
	            return new LeadNotFoundException("Lead not found with ID: " + id);
	        });

	        logger.info("Lead found: ID={}, Current Status={}", lead.getId(), lead.getLeadStatus());

	        // ---------------------- CASE 1: Make Positive -----------------------
	        if (action == 1) {

	            if (!lead.getLeadStatus().equalsIgnoreCase("negative")) {
	                logger.warn("Support attempted POSITIVE update for non-negative lead ID={}", id);
	                throw new RuntimeException("Only NEGATIVE leads can be made POSITIVE by support team");
	            }

	            logger.info("Support Team marking lead ID={} as POSITIVE", id);

	            lead.setLeadStatus("positive");
	            leadRepo.save(lead);

	            logger.info("Lead ID={} updated to POSITIVE — converting to customer", id);
	            leadService.convertLeadToCustomer(lead);

	            return "Lead converted to customer by Support Team";
	        }

	        // ---------------------- CASE 2: Close Lead -----------------------
	        if (action == 2) {

	            if (!lead.getLeadStatus().equalsIgnoreCase("negative")) {
	                logger.warn("Support attempted close for non-negative lead ID={}", id);
	                throw new RuntimeException("Only NEGATIVE leads can be closed");
	            }

	            if (reason == null || reason.isBlank()) {
	                logger.error("Close failed — missing reason for lead ID={}", id);
	                throw new RuntimeException("Reason is required for closing a lead");
	            }

	            logger.info("Closing NEGATIVE lead ID={} permanently", id);

	            ClosedLead closed = new ClosedLead();
	            closed.setName(lead.getName());
	            closed.setEmail(lead.getEmail());
	            closed.setMobile(lead.getMobNo());
	            closed.setSource(lead.getSource());
	            closed.setClosedDate(LocalDate.now());
	            closed.setClosedReason(reason);

	            closedLeadRepo.save(closed);
	            logger.info("Closed lead saved: ID={}, Reason={}", id, reason);

	            leadRepo.delete(lead);
	            logger.info("Lead ID={} removed from active leads", id);

	            return "Lead closed successfully";
	        }

	        // invalid action
	        logger.error("Invalid action={} for Lead ID={}", action, id);
	        throw new RuntimeException("Invalid action. Use 1 = Positive, 2 = Close");
	    }
	  public Support_entity findByEmail(String email) {
		    return S_repo.findByEmail(email);
		}
	

	@Override
	public List<Support_entity> findAll() {
		logger.info("Fetching all support entities.");
		List<Support_entity> supportEntities = S_repo.findAll();
		logger.info("Found {} support entities.", supportEntities.size());
		return supportEntities;
	}

	public Support_entity createSupport(Support_entity supportEntity) {
		logger.info("Creating new support entity: {}", supportEntity.getSalesrepname());

		Support_entity createdSupport = S_repo.save(supportEntity);

		logger.info("Support entity created with ID: {}", createdSupport.getSalesrepID());
		return createdSupport;
	}
	

	public String deleteSupport(Long salesrepID) {
		logger.info("Attempting to delete support entity with ID: {}", salesrepID);

		Support_entity support = S_repo.findById(salesrepID).orElseThrow(() -> {
			logger.error("Support entity not found with ID: {}", salesrepID);
			return new RuntimeException("Support entity not found");
		});

		S_repo.delete(support);
		logger.info("Support entity with ID: {} has been deleted.", salesrepID);

		return "Support entity deleted successfully";
	}

}
