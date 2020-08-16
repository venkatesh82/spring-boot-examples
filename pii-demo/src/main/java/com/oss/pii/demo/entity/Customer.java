package com.oss.pii.demo.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.oss.pii.demo.annotations.Mask;
import com.oss.pii.demo.annotations.Sensitive;
import com.oss.pii.demo.config.MaskSerializer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Sensitive
@JsonSerialize(using = MaskSerializer.class)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Mask(keepFirstDigits = 2,keepLastDigits=1)
    private String firstName;

    private String lastName;

    @Mask(keepFirstDigits = 2,keepLastDigits=4, maskChar = 'X')
    private String creditCardNumber;

    public Customer(){

    }

    public Customer(String firstName, String lastName, String creditCardNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.creditCardNumber = creditCardNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Customer{");
        sb.append("id=").append(id);
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", creditCardNumber='").append(creditCardNumber).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
