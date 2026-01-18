package CRM.Imple;

import CRM.Entity.Admin;
import CRM.Repository.AdminRepository;
import CRM.Service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

	private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

	@Autowired
	private AdminRepository adminRepository;

	@Override
	public Admin createAdmin(Admin admin) {
		logger.info("Attempting to create a new admin with name: {}", admin.getAdmin_name());

		try {

			Admin savedAdmin = adminRepository.save(admin);
			logger.info("Admin created successfully with ID: {}", savedAdmin.getID());
			return savedAdmin;
		} catch (Exception e) {
			logger.error("Error creating admin with name: {}. Error: {}", admin.getAdmin_name(), e.getMessage());
			throw e;
		}
	}
	
	public Admin findByEmail(String email) {
        return adminRepository.findByEmail(email);
    }

	@Override
	public String deleteAdmin(Long id) {
		logger.info("Attempting to delete admin with ID: {}", id);

		try {
			Admin admin = adminRepository.findById(id)
					.orElseThrow(() -> new RuntimeException("Admin not found with ID: " + id));

			adminRepository.delete(admin);
			logger.info("Admin with ID: {} has been deleted successfully", id);
			return "Admin deleted successfully";
		} catch (RuntimeException e) {
			logger.error("Error deleting admin with ID: {}. Error: {}", id, e.getMessage());
			throw e;
		}
	}
	
}
