package com.unifiedresto.platform.dto;

public class RestaurantRequestDTO {
  //   private Integer orderNumber;
     private String name;
     private String cnpj;
     private String email;
     private String login;
     private String password;
     private AddressRequestDTO address;// Objeto


     public String getName() {
         return name;
     }

     public void setName(String name) {
         this.name = name;
     }

     public String getCnpj() {
         return cnpj;
     }

     public void setCnpj(String cnpj) {
         this.cnpj = cnpj;
     }

     public String getEmail() {
         return email;
     }

     public void setEmail(String email) {
         this.email = email;
     }

     public String getLogin() {
         return login;
     }

     public void setLogin(String login) {
         this.login = login;
     }
     // senha chega em texto plano → Service criptografa
     public String getPassword() {
         return password;
     }

     public void setPassword(String password) {
         this.password = password;
     }

     public AddressRequestDTO getAddress() {
         return address;
     }

     public void setAddress(AddressRequestDTO address) {
         this.address = address;
     }
}
