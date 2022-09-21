package com.kalsym.analytic.service.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@Table(name = "total_unique_user_overall")
public class TotalUniqueUserOverall implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    
    private Date dt;    
    private Integer totalUnique;  
    private Integer totalUniqueGuest;
    private String country;
}
