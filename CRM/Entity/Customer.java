package CRM.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
 
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String mobNo;

    @Column(name = "conversion_time")
    private LocalDateTime conversionTime;

  
    public Customer(String name, String email, String mobNo, LocalDateTime conversionTime) {
        this.name = name;
        this.email = email;
        this.mobNo = mobNo;
        
        this.conversionTime = conversionTime;
    }

   
}
