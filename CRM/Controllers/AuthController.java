package CRM.Controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import CRM.Entity.Admin;
import CRM.Entity.Service_entity;
import CRM.Entity.Support_entity;
import CRM.Service.AdminService;
import CRM.Service.Service_I;
import CRM.Service.SupportService;
import CRM.security.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired private AdminService adminService;
    @Autowired private SupportService supportService;
    @Autowired private Service_I serviceTeam;

    @Autowired private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");

        // Check ADMIN
        Admin admin = adminService.findByEmail(email);
        if (admin != null && admin.getPassword().equals(password)) {
            String token = jwtUtil.generateToken(admin.getEmail(),"ADMIN");
            return ResponseEntity.ok(Map.of("token",token,"role","ADMIN","name",admin.getAdmin_name()));
        }

        // SUPPORT
        Support_entity support = supportService.findByEmail(email);
        if (support != null && support.getPassword().equals(password)) {
            String token = jwtUtil.generateToken(support.getEmail(),"SUPPORT");
            return ResponseEntity.ok(Map.of("token",token,"role","SUPPORT","name",support.getSalesrepname()));
        }

        // SERVICE
        Service_entity service = serviceTeam.findByEmail(email);
        if (service != null && service.getPassword().equals(password)) {
            String token = jwtUtil.generateToken(service.getEmail(),"SERVICE");
            return ResponseEntity.ok(Map.of("token",token,"role","SERVICE","name",service.getSeller_name()));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
    }


}

