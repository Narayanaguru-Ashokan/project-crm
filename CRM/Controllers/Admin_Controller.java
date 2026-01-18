package CRM.Controllers;

import CRM.Entity.Admin;
import CRM.Entity.Lead;
import CRM.Entity.Service_entity;
import CRM.Entity.Support_entity;
import CRM.Service.AdminService;
import CRM.Service.Lead_service;
import CRM.Service.Service_I;
import CRM.Service.SupportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class Admin_Controller {

	private final Lead_service leadService;
	private final SupportService supportService;
	private final AdminService adminService;
	private final Service_I service;

	public Admin_Controller(Lead_service leadService, SupportService supportService, AdminService adminService,
			Service_I service) {
		this.leadService = leadService;
		this.supportService = supportService;
		this.adminService = adminService;
		this.service = service;
	}

	@GetMapping("/filter/leadsrc")
	public ResponseEntity<List<Lead>> getLeadsFilteredBySource(@RequestParam String source) {
		List<Lead> leads = leadService.getLeadsBySource(source);

		if (leads.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(leads, HttpStatus.OK);
	}

	@GetMapping("/filter/leadstatus")
	public ResponseEntity<List<Lead>> getLeadsFilteredByStatus(@RequestParam String status) {
		List<Lead> leads = leadService.getLeadsByStatus(status);

		if (leads.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(leads, HttpStatus.OK);
	}
	@PostMapping("/lead/update-status")
	public ResponseEntity<?> updateLeadStatus(
	        @RequestParam Long leadId,
	        @RequestParam String status) {

	    // NEW → POSITIVE / NEW → NEGATIVE goes to Lead Service
	    if(status.equalsIgnoreCase("positive") || status.equalsIgnoreCase("negative")){
	        return ResponseEntity.ok(leadService.updateLeadStatus(leadId, status));
	    }

	    return ResponseEntity.badRequest().body("Invalid status for admin update");
	}



	@GetMapping("/sales")
	public ResponseEntity<List<Support_entity>> getSalesRepresentatives() {
		List<Support_entity> salesRepresentatives = supportService.findAll();

		if (salesRepresentatives.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(salesRepresentatives, HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<Admin> createAdmin(@RequestBody Admin admin) {

		Admin createdAdmin = adminService.createAdmin(admin);
		return new ResponseEntity<>(createdAdmin, HttpStatus.CREATED);

	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteAdmin(@PathVariable Long id) {
		try {
			String result = adminService.deleteAdmin(id);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (RuntimeException e) {
			return new ResponseEntity<>("Admin not found", HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/service/create")
	public ResponseEntity<Service_entity> createService(@RequestBody Service_entity serviceEntity) {

		Service_entity createdService = service.createService(serviceEntity);
		return new ResponseEntity<>(createdService, HttpStatus.CREATED);

	}

	@DeleteMapping("/service/delete/{id}")
	public ResponseEntity<String> deleteService(@PathVariable Long id) {
		try {
			String result = service.deleteService(id);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (RuntimeException e) {
			return new ResponseEntity<>("Service entity not found", HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/all-support")
	public ResponseEntity<List<Support_entity>> getAllSupportEntities() {
		List<Support_entity> supportEntities = supportService.findAll();

		if (supportEntities.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(supportEntities, HttpStatus.OK);
	}

	@PostMapping("/support/create")
	public ResponseEntity<Support_entity> createSupport(@RequestBody Support_entity supportEntity) {

		Support_entity createdSupport = supportService.createSupport(supportEntity);
		return new ResponseEntity<>(createdSupport, HttpStatus.CREATED);

	}

	@DeleteMapping("/support/delete/{id}")
	public ResponseEntity<String> deleteSupport(@PathVariable Long id) {
		try {
			String result = supportService.deleteSupport(id);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (RuntimeException e) {
			return new ResponseEntity<>("Support entity not found", HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/negative-leads")
	public ResponseEntity<List<Lead>> getNegativeLeads() {
		List<Lead> negativeLeads = supportService.getNegativeLeads();

		if (negativeLeads.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(negativeLeads, HttpStatus.OK);
	}

	
}
