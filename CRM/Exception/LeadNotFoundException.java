package CRM.Exception;


public class LeadNotFoundException extends RuntimeException {

    
    public LeadNotFoundException(String message) {
        super(message);
    }
}
