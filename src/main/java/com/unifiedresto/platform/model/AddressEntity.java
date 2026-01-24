package com.unifiedresto.platform.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
 //TODO  alterra para lombok
@Entity
@Table(name ="address")
public class AddressEntity {
  //marca compor como chave primaria
  @Id
  //gera ID automatic
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long id;

    @Column(name = "street", nullable = false, length = 50)
    private String street;

    @Column(name = "number", nullable = false, length = 15)
    private String number;

    @Column(name = "city", nullable = false, length = 50)
    private String city;

    @Column(name = "postal_code", nullable = false, length = 50)
    private String postalCode;



    public Long getId() {
      return id;
    }

    public String getStreet() {
      return street;
    }

    public void setStreet(String street) {
      this.street = street;
    }

    public String getNumber() {
      return number;
    }

    public void setNumber(String number) {
      this.number = number;
    }

    public String getCity() {
      return city;
    }

    public void setCity(String city) {
      this.city = city;
    }

    public String getPostalCode() {
      return postalCode;
    }

    public void setPostalCode(String postalCode) {
      this.postalCode = postalCode;
    }
  }