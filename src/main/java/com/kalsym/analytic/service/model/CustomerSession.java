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
@Table(name = "customer_session")
public class CustomerSession implements Serializable {
    
    @Id
    private String sessionId;
    
    private Date created;
    private Date updated;
    
    private String latitude;
    private String longitude;
    private String address;
    private String city;
    private String postcode;
    private String state;
    private String country;
}
