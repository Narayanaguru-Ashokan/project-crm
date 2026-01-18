package CRM.Controllers;

import CRM.Entity.Lead;
import CRM.Service.Lead_service;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/leads")
@CrossOrigin(origins="*")
public class LeadController {

    private final Lead_service service;

    public LeadController(Lead_service service) {
        this.service = service;
    }

    // ========== CREATE LEAD ==========
    @PostMapping("/save")
    public Lead saveLead(@RequestBody Lead lead) {
        return service.saveLead(lead);
    }

    // ========== GET ALL LEADS ==========
    @GetMapping("/all")
    public List<Lead> getAllLeads() {
        return service.getAllLeads();
    }

    // ========== FILTER BY STATUS ==========
    @GetMapping("/status/{status}")
    public List<Lead> getByStatus(@PathVariable String status) {
        return service.getLeadsByStatus(status);
    }

    // ========== SERVICE TEAM (NEW LEADS) ==========
    @GetMapping("/service")
    public List<Lead> getServiceLeads() {
        return service.getLeadsByStatus("new");
    }

    // ========== SUPPORT TEAM (NEGATIVE LEADS) ==========
    @GetMapping("/support")
    public List<Lead> getSupportLeads() {
        return service.getLeadsByStatus("negative");
    }

    // ========== UPDATE STATUS ==========
    @PutMapping("/{id}/status")
    public String updateStatus(@PathVariable Long id, @RequestParam String status) {
        return service.updateLeadStatus(id, status);
    }

    // ========== DELETE LEAD ==========
    @DeleteMapping("/delete/{id}")
    public String deleteLead(@PathVariable Long id) {
        return service.deleteLead(id);
    }
}
