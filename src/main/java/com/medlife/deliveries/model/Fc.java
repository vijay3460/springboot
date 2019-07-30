package com.medlife.deliveries.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "fc")
public class Fc extends BaseModel {

    public Fc() {
    }

    @Column(name = "name")
    private String name;

    @Column(name =  "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "pincode")
    private String pincode;

    @Column(name = "country")
    private String country;

    @Column(name = "phone")
    private String phone;

}
