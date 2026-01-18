package CRM.message_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import CRM.Repository.ClosedLeadRepository;
import CRM.Repository.CustomerRepository;
import CRM.Repository.LeadRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.springframework.core.io.FileSystemResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

 
enum EmailSource {
	Customer, Lead, ClosedLead
}

@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private CustomerRepository C_repo;
	@Autowired
	private LeadRepository L_repo;
	@Autowired
	private ClosedLeadRepository CL_repo;

	
	 private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

	public List<String> getEmails(EmailSource source) {

		switch (source) {

		case Customer:

			return C_repo.getAllEmails();

		case Lead:
			return L_repo.getAllEmails();
		case ClosedLead:
			return CL_repo.getAllEmails();
		default:
			return Collections.emptyList();
		}
	}

	public void sendEmailsWithoutAttachment(EmailSource source, String subject, String body) {
		try {

			List<String> toList = getEmails(source);

			if (toList.isEmpty()) {
				logger.warn("No email addresses found for source: {}", source);

				return;
			}

			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, false);

			helper.setTo("no-reply@example.com");

			helper.setBcc(toList.toArray(new String[0]));

			helper.setSubject(subject);
			helper.setText(body);

			javaMailSender.send(message);
			logger.info("Email sent successfully to {} recipients via BCC!", toList.size());


		} catch (MessagingException | MailException e) {
			e.printStackTrace();
		}
	}

	public void sendEmailsWithAttachment(EmailSource source, String subject, String body, File attachment) {
		try {
			 
			List<String> toList = getEmails(source);

			if (toList.isEmpty()) {
				logger.warn("No email addresses found for source: {}", source);

				return;
			}

			MimeMessage message = javaMailSender.createMimeMessage();

			 
			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			 
			helper.setTo("no-reply@example.com");
 
			helper.setBcc(toList.toArray(new String[0]));

			helper.setSubject(subject);
			helper.setText(body);

			// Add attachment
			if (attachment != null && attachment.exists()) {
				FileSystemResource file = new FileSystemResource(attachment);
				helper.addAttachment(attachment.getName(), file);
			}

			javaMailSender.send(message);
			logger.info("Email sent successfully to {} recipients via BCC!", toList.size());

		} catch (MessagingException | MailException e) {
			e.printStackTrace();
		}
	}

	public void sendEmailWithAttachment(String to, String subject, String body, String filePath) {
		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body);

			File file = new File(filePath);
			helper.addAttachment("Attachment", file);

			javaMailSender.send(message);

			logger.info("Email sent   successfully with attachment");
		} catch (MessagingException | MailException e) {
			e.printStackTrace();
		}
	}

	public void sendEmailWithoutAttachment(String to, String subject, String body) {
		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, false);

			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body);

			javaMailSender.send(message);

			logger.info("Email sent  successfully without attachment");
		} catch (MessagingException | MailException e) {
			e.printStackTrace();
		}
	}

	public void sendWelcomeEmail(String toEmail) {
		String subject = "Welcome to our Service!";
		String body = "Hello,\n\nWelcome to our service! We're excited to have you on board. If you have any questions, feel free to reach out.\n\nBest regards,\nYour Team\n\"Automated message: Please do not respond to this email.\"";

		sendEmailWithoutAttachment(toEmail, subject, body);
	}
}
