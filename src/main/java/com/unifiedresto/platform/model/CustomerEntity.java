package com.unifiedresto.platform.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import java.time.LocalDateTime;

@Entity
//regras de unicidade no banco de dado
@Table(
  name = "customer")
public class CustomerEntity { // vai representar a tabela
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "cpf", nullable = false, length = 15, unique = true)
    private String cpf;

    @Column(name = "email", nullable = false, length = 50, unique = true)
    private String email;

    @Column(name = "login", nullable = false, length = 50, unique = true)
    private String login;
    @JsonIgnore
    @Column(name = "password", nullable = false, length = 255)
    private String password;

    // essa atualizacao vai ser mudando de acordo com cada regsitro.
    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    /* Relacionamento um-para-um com Address.
     * Cada Customer deve possuir exatamente um Address associado.
     * A coluna address_id é uma chave estrangeira obrigatória e única,
     * garantindo que um endereço nao possa ser compartilhado por varios clientes*/
     @OneToOne
     @JoinColumn( name = "address_id",nullable = false, unique = true,
     foreignKey = @ForeignKey(name = "fk_customer_address"))
     private AddressEntity address;

     // foreignKey associacao
    @PrePersist
    @PreUpdate
    private void updateTimestamp() {
      this.lastUpdate = LocalDateTime.now();
    }
    //gettes and setters

    // Lembrando que
    public Long getId() {
      return id;
    }

    public void setId (Long id){
      this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName (String name){
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public  String getCpf (){
      return cpf;
    }

    public void setCpf (String cpf){
      this.cpf = cpf;
    }

    public  String getEmail(){
      return  email;
    }

    public void setEmail (String email){
      this.email = email;
    }

   public String getPassword() {
     return password;
    }

//    public void setPassword(String hashedPassword) {
//        this.password = hashedPassword;
//    }

    public void setPassword(String Password) {
        this.password = Password;
    }

    public LocalDateTime getLastUpdate() {
      return lastUpdate;
    }

    public  void setLastUpdate (LocalDateTime lastUpdate){
      this.lastUpdate = lastUpdate;
    }

    public AddressEntity getAddress() {
      return address;
    }

   public void setAddress(AddressEntity address) {
      this.address = address;
   }
}
