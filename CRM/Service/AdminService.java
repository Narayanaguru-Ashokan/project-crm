package CRM.Service;

import CRM.Entity.Admin;

public interface AdminService {
	Admin createAdmin(Admin admin);

	String deleteAdmin(Long id);
	
	Admin findByEmail(String email);
}
