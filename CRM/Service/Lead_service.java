package CRM.Service;

import java.util.List;
import CRM.Entity.Lead;

public interface Lead_service {

    Lead saveLead(Lead lead);

    List<Lead> getAllLeads();

    List<Lead> getLeadsByStatus(String status);

    String updateLeadStatus(Long id, String status);

    void convertLeadToCustomer(Lead lead);

    String deleteLead(Long id);
    
    List<Lead>getLeadsBySource(String source);
    
    

}
