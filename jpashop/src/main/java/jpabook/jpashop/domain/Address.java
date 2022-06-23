package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    protected Address() {
    }

    public Address(String city, String streed, String zipcode) {
        this.city = city;
        this.street = streed;
        this.zipcode = zipcode;
    }
}
