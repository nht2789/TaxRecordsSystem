package com.trs.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "taxpayers")
public class TaxPayer {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
        
        @Column(unique = true, nullable = false)
        private Long taxFileNumber;
        
        @Column(nullable = false)
	private String firstName;
        
        @Column(nullable = false)
	private String lastName;
        @Column(nullable = false)
        private String address;
        
        @Column(nullable = false)
        private String phone;

        @Column(nullable = false)
        private Long income;

        @Column(nullable = false)
        private Long deductibleAmount;
        
        @Column(nullable = false)
        private Long taxHeld;
        
        @Column(nullable = false)
        private Long taxToBeReturned;

    public TaxPayer(Long taxFileNnumber, String firstName, String lastName, String address, String phone, Long income, Long deductibleAmount, Long taxHeld, Long taxToBeReturned) {
        this.taxFileNumber = taxFileNnumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        this.income = income;
        this.deductibleAmount = deductibleAmount;
        this.taxHeld = taxHeld;
        this.taxToBeReturned = taxToBeReturned;
    }

    public TaxPayer() {
    }   
        
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTaxFileNumber() {
        return taxFileNumber;
    }

    public void setTaxFileNumber(Long taxFileNumber) {
        this.taxFileNumber = taxFileNumber;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getIncome() {
        return income;
    }

    public void setIncome(Long income) {
        this.income = income;
    }

    public Long getDeductibleAmount() {
        return deductibleAmount;
    }

    public void setDeductibleAmount(Long deductibleAmount) {
        this.deductibleAmount = deductibleAmount;
    }

    public Long getTaxHeld() {
        return taxHeld;
    }

    public void setTaxHeld(Long taxHeld) {
        this.taxHeld = taxHeld;
    }

    public Long getTaxToBeReturned() {
        return taxToBeReturned;
    }

    public void setTaxToBeReturned(Long taxToBeReturned) {
        this.taxToBeReturned = taxToBeReturned;
    }

    @Override
    public String toString() {
        return "TaxPayer{" + "taxFileNnumber=" + taxFileNumber + ", firstName=" + firstName + ", lastName=" + lastName + ", address=" + address + ", phone=" + phone + ", income=" + income + ", deductibleAmount=" + deductibleAmount + ", taxHeld=" + taxHeld + ", taxToBeReturned=" + taxToBeReturned + '}';
    }

}
