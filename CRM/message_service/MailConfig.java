package CRM.message_service;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        
       
        mailSender.setHost("smtp.gmail.com");  
        mailSender.setPort(587);
        
        
        mailSender.setUsername("your_email@gmail.com");  
        mailSender.setPassword("your_email_password");  
        
        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.debug", "true");  

        return mailSender;
    }
}

