package com.s21.devops.sample.hotelservice.Model;

import javax.persistence.*;

@Entity
@Table(name = "countries", schema = "public")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer country_id;

    @Column(name = "country")
    private String country;


    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getCountry_id() {
        return country_id;
    }

    public void setCountry_id(Integer country_id) {
        this.country_id = country_id;
    }
}
