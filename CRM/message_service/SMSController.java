//package CRM.message_service;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/sms")
//public class SMSController {
//
//    @Autowired
//    private SMS_Service smsService;
//
//    
//    @PostMapping("/send")
//    public String sendSMS(@RequestParam String toPhoneNumber, @RequestParam String message) {
//        smsService.sendSMS(toPhoneNumber, message);
//        return "SMS sent successfully to " + toPhoneNumber;
//    }
//}
//
//
//
