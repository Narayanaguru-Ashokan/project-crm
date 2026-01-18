package CRM.Entity;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Closed_Leads")
@Data
public class ClosedLead {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	private String name;
	private String mobile;
	private String email;
	private String source;
	private String closedReason;
	private LocalDate closedDate;
	
}
