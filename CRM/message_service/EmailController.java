package CRM.message_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

     @PostMapping("/send")
    public ResponseEntity<String> sendEmail(
            @RequestParam("source") EmailSource source,
            @RequestParam("subject") String subject,
            @RequestParam("body") String body) {

        emailService.sendEmailsWithoutAttachment(source, subject, body);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Email request submitted for source: " + source);
    }

     @PostMapping("/send-with-attachment")
    public ResponseEntity<String> sendEmailWithAttachment(
            @RequestParam("source") EmailSource source,
            @RequestParam("subject") String subject,
            @RequestParam("body") String body,
            @RequestParam("file") MultipartFile file) {

        try {
             File tempFile = File.createTempFile("upload-", file.getOriginalFilename());
            file.transferTo(tempFile);

            emailService.sendEmailsWithAttachment(source, subject, body, tempFile);

            tempFile.deleteOnExit();

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("Email with attachment request submitted for source: " + source);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to process the uploaded file.");
        }
    }
 
    @PostMapping("/send-welcome")
    public ResponseEntity<String> sendWelcomeEmail(@RequestParam("to") String to) {
        emailService.sendWelcomeEmail(to);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Welcome email sent to: " + to);
    }
 
    @PostMapping("/send-single")
    public ResponseEntity<String> sendEmailToSingle(
            @RequestParam("to") String to,
            @RequestParam("subject") String subject,
            @RequestParam("body") String body) {

        emailService.sendEmailWithoutAttachment(to, subject, body);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Email sent to: " + to);
    }
 
    @PostMapping("/send-single-with-attachment")
    public ResponseEntity<String> sendEmailToSingleWithAttachment(
            @RequestParam("to") String to,
            @RequestParam("subject") String subject,
            @RequestParam("body") String body,
            @RequestParam("file") MultipartFile file) {

        try {
            File tempFile = File.createTempFile("upload-", file.getOriginalFilename());
            file.transferTo(tempFile);

            emailService.sendEmailWithAttachment(to, subject, body, tempFile.getAbsolutePath());

            tempFile.deleteOnExit();

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("Email with attachment sent to: " + to);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to process the uploaded file.");
        }
    }
}
