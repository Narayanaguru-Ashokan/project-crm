package CRM.Imple;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import CRM.Entity.Customer;
import CRM.Entity.Lead;
import CRM.Repository.CustomerRepository;
import CRM.Repository.LeadRepository;
import CRM.Service.Lead_service;
import CRM.message_service.EmailService;

@Service
public class Lead_impl implements Lead_service {

    private static final Logger logger = LoggerFactory.getLogger(Lead_impl.class);

    @Autowired private LeadRepository leadRepo;
    @Autowired private CustomerRepository customerRepo;
    @Autowired private EmailService emailService;

    // -------------------------------------------------------
    // 1) CREATE LEAD (default NEW)
    // -------------------------------------------------------
    @Override
    public Lead saveLead(Lead lead) {
        lead.setLeadStatus("new");
        Lead saved = leadRepo.save(lead);
        emailService.sendWelcomeEmail(saved.getEmail());
        return saved;
    }

    // -------------------------------------------------------
    // 2) FETCH LIST METHODS
    // -------------------------------------------------------
    public List<Lead> getAllLeads() {
        return leadRepo.findAll();
    }
    
    @Override
    public List<Lead> getLeadsByStatus(String status) {
        return leadRepo.findByLeadStatus(status);
    }
    
	@Override
    public List<Lead>getLeadsBySource(String source){
    	return leadRepo.findBySourceIgnoreCase(source);
    }
    
   

    // -------------------------------------------------------
    // 3) UPDATE LEAD STATUS (Full Logic Applied)
    // -------------------------------------------------------
    @Override
    public String updateLeadStatus(Long id, String status) {

        Lead lead = leadRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Lead Not Found"));

        String current = lead.getLeadStatus().toLowerCase();
        String next = status.toLowerCase();

        // Validation to avoid wrong transitions
        if (current.equals("new") && !(next.equals("positive") || next.equals("negative")))
            return "NEW can only move to POSITIVE or NEGATIVE";

        if (current.equals("positive") && !next.equals("customer"))
            return "POSITIVE can only convert to CUSTOMER";

        if (current.equals("negative") && !(next.equals("customer") || next.equals("closed")))
            return "NEGATIVE can convert to CUSTOMER or CLOSED";

        // Apply update
        lead.setLeadStatus(status);
        leadRepo.save(lead);

        // Convert to customer
        if (next.equals("customer")) {
            convertLeadToCustomer(lead);
            return "Lead converted to customer!";
        }

        return "Lead updated to status: " + status;
    }

    // -------------------------------------------------------
    // 4) CONVERT LEAD INTO CUSTOMER
    // -------------------------------------------------------
    @Override
    public void convertLeadToCustomer(Lead lead) {

        Customer customer = new Customer(
                lead.getName(),
                lead.getEmail(),
                lead.getMobNo(),
                LocalDateTime.now()
        );

        customerRepo.save(customer);
        leadRepo.delete(lead);
    }

    // -------------------------------------------------------
    // 5) DELETE LEAD
    // -------------------------------------------------------
    @Override
    public String deleteLead(Long id) {
        if (!leadRepo.existsById(id)) return "Lead not found";
        leadRepo.deleteById(id);
        return "Lead deleted";
    }

	


}
