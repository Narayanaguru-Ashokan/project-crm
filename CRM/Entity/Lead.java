package CRM.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "leads")
@Data
public class Lead {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(name = "mob_no", nullable = false)
	private String mobNo;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String source;

	@Column(name = "lead_status")
	private String leadStatus;

	 
}



