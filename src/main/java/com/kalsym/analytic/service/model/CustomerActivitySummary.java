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
@Table(name = "customer_activities_summary")
public class CustomerActivitySummary implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    
    private Date dt;
    private String storeId;
    private String browser;
    private String device;
    private String os;
    private String page;    
    private Integer totalCount;
    private Integer totalUniqueUser;    
}
