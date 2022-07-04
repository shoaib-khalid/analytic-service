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
@Table(name = "customer_activities")
public class CustomerActivity implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    
    private String storeId;
    private String customerId;
    private String sessionId;
    private String pageVisited;
    private String ip;
    private String os;
    private String deviceModel;
    private String browserType;
    private String errorOccur;
    private String errorType;
    private Date created;
    private String latitude;
    private String longitude;
}
