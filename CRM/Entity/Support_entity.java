package CRM.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Support_entity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long salesrepID;

	@Column(nullable = false)
	private String salesrepname;

	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false)
    private String password;   

    @Column(nullable = false)
    private String role = "SUPPORT";  

	 
}
