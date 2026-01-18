package CRM.Controllers;

import CRM.Entity.Lead;
import CRM.Entity.Service_entity;
import CRM.Service.Service_I;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/services")
public class Service_controller {

    @Autowired
    private Service_I service;

    
    @PostMapping("/update-status")
    public ResponseEntity<Lead> updateLeadStatus(@RequestParam Long leadId, @RequestParam String status) {
        Lead updatedLead = service.updateLeadStatus(leadId, status);
        return new ResponseEntity<>(updatedLead, HttpStatus.OK);
    }

    
    @PostMapping("/create")
    public ResponseEntity<Service_entity> createService(@RequestBody Service_entity serviceEntity) {
       
            Service_entity createdService = service.createService(serviceEntity);  
            return new ResponseEntity<>(createdService, HttpStatus.CREATED); 
       
    }

    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteService(@PathVariable Long id) {
        try {
            String result = service.deleteService(id);  
            return new ResponseEntity<>(result, HttpStatus.OK);  
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Service entity not found", HttpStatus.NOT_FOUND);  
        }
    }
}
