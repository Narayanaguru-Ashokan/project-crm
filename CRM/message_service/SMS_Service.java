//package CRM.message_service;
//
//	import com.twilio.Twilio;
//	import com.twilio.rest.api.v2010.account.Message;
//	import com.twilio.type.PhoneNumber;
//	import org.springframework.beans.factory.annotation.Value;
//	import org.springframework.stereotype.Service;
//
//	@Service
//	public class SMS_Service {
//
//	    @Value("${twilio.account_sid}")
//	    private String accountSid;
//
//	    @Value("${twilio.auth_token}")
//	    private String authToken;
//
//	    @Value("${twilio.phone_number}")
//	    private String twilioPhoneNumber;
//
//	    
//	    public SMS_Service() {
//	        Twilio.init(accountSid, authToken);
//	    }
//
//	    public void sendSMS(String toPhoneNumber, String messageContent) {
//	        Message message = Message.creator(
//	                new PhoneNumber(toPhoneNumber),  
//	                new PhoneNumber(twilioPhoneNumber), 
//	                messageContent
//	        ).create();
//
//	        System.out.println("SMS sent: " + message.getSid());
//	    }
//	}
//
//
