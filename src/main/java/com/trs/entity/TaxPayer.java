package com.trs.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "taxpayers")
public class TaxPayer implements Comparable<TaxPayer>{
    
        @Id
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
        private Long income = 0L;

        @Column(nullable = false)
        private Long deductibleAmount = 0L;
        
        @Column(nullable = false)
        private Long taxHeld = 0L;
        
        @Column(nullable = false)
        private Long taxToBeReturned = 0L;

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
        taxHeld = calculateTax(income);
        return taxHeld;
    }

    public Long getTaxToBeReturned() {
        long taxable = income - deductibleAmount;
        long actualTax = calculateTax(taxable);
        this.taxToBeReturned = getTaxHeld() - actualTax;
        return taxToBeReturned;
    }

    @Override
    public String toString() {
        return "TaxPayer{" + "taxFileNnumber=" + taxFileNumber + ", firstName=" + firstName + ", lastName=" + lastName + ", address=" + address + ", phone=" + phone + ", income=" + income + ", deductibleAmount=" + deductibleAmount + ", taxHeld=" + taxHeld + ", taxToBeReturned=" + taxToBeReturned + '}';
    }

    @Override
    public int compareTo(TaxPayer o) {
        return Long.compare(getTaxFileNumber(), o.getTaxFileNumber());
    }

    public String getFullName(){
        return this.firstName + " " + this.lastName; 
    }
    
    private long calculateTax(long income){
        long result = 0;
        if (income <= 18200){
            return 0L;
        } else if(income > 18200 && income <= 45000){
            result = (long)((income - 18200)*19/100);
        } else if (income > 45000 && income <= 120000){
            result = (long) ((income - 45000)*32.5/100 + 5092);
        } else if (income > 120000 && income <= 1800000  ) {
            result = (long) ((income - 120000)*37/100 + 29467 );
        } else if (income > 180000){
            result = (long) ((income - 180000)*45/100 + 51667);
        }
        return result;
    }
}
