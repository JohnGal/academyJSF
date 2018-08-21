package models;

import java.io.Serializable;
import java.util.Date;

public class Customer implements Serializable {
    private static final long serialVersionUID = -2130366538047557980L;
    private String firstName;
    private String lastName;
    private String userName;
    private Date birthDate;
    private String country;
    private String city;
    private String street;
    private String zipCode;
    private String phoneNumber;
    private String email;
    private boolean hasAcceptedTerms;
    private Long id;

    public Customer() {
    }

    public Customer(Customer customer) {
        firstName = customer.getFirstName();
        lastName = customer.getLastName();
        userName = customer.getUserName();
        birthDate = new Date(customer.getBirthDate().getTime());
        country = customer.getCountry();
        city = customer.getCity();
        street = customer.getStreet();
        zipCode = customer.getZipCode();
        phoneNumber = customer.getPhoneNumber();
        email = customer.getEmail();
        hasAcceptedTerms = customer.getHasAcceptedTerms();
        id = customer.getId();
    }

    public Customer(String userName) {
        this.userName = userName;
    }

    public Customer(Long id) {
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getHasAcceptedTerms() {
        return hasAcceptedTerms;
    }

    public void setHasAcceptedTerms(boolean hasAcceptedTerms) {
        this.hasAcceptedTerms = hasAcceptedTerms;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        return id != null ? id.equals(customer.id) : customer.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
