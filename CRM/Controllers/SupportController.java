package CRM.Controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import CRM.Entity.ClosedLead;
import CRM.Entity.Lead;
import CRM.Entity.Support_entity;
import CRM.Repository.ClosedLeadRepository;
import CRM.Service.SupportService;

@RestController
@RequestMapping("/support")
public class SupportController {
	
	

	private final SupportService supportService;
	private final ClosedLeadRepository closedLeadRepo;

	public SupportController(SupportService supportService, ClosedLeadRepository closedLeadRepo) {
	    this.supportService = supportService;
	    this.closedLeadRepo = closedLeadRepo;
	}


	@GetMapping("/negative-leads")
	public ResponseEntity<List<Lead>> getNegativeLeads() {
		List<Lead> negativeLeads = supportService.getNegativeLeads();

		if (negativeLeads.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(negativeLeads, HttpStatus.OK);
	}
	

	@PutMapping("/lead/action/{id}")
	public ResponseEntity<?> leadAction(@PathVariable Long id, @RequestParam int op,
			@RequestParam(required = false) String reason) {

		try {
			Object result = supportService.handleLeadAction(id, op, reason);
			return ResponseEntity.ok(result);

		} catch (RuntimeException ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
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

	@PostMapping("/create")
	public ResponseEntity<Support_entity> createSupport(@RequestBody Support_entity supportEntity) {

		Support_entity createdSupport = supportService.createSupport(supportEntity);
		return new ResponseEntity<>(createdSupport, HttpStatus.CREATED);

	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteSupport(@PathVariable Long id) {
		try {
			String result = supportService.deleteSupport(id);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (RuntimeException e) {
			return new ResponseEntity<>("Support entity not found", HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/convert")
	public ResponseEntity<?> convertNegativeLead(@RequestParam Long id) {
	    try {
	        Object result = supportService.handleLeadAction(id, 1, null);
	        return ResponseEntity.ok(result);
	    } catch (RuntimeException ex) {
	        return ResponseEntity.badRequest().body(ex.getMessage());
	    }
	}
	
	@PostMapping("/close/{id}")
	public ResponseEntity<?> closeLead(
	        @PathVariable Long id,
	        @RequestBody(required = true) Map<String, String> body) {

	    try {
	        String reason = body.get("reason");
	        Object result = supportService.handleLeadAction(id, 2, reason);
	        return ResponseEntity.ok(result);
	    } catch (RuntimeException ex) {
	        return ResponseEntity.badRequest().body(ex.getMessage());
	    }
	}
	
	@GetMapping("/closed-leads")
	public ResponseEntity<List<ClosedLead>> getClosedLeads() {
	    List<ClosedLead> list = closedLeadRepo.findAll();
	    return ResponseEntity.ok(list);
	}




}
