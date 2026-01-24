package com.unifiedresto.platform.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "restaurant")
public class RestaurantEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "restaurant_id")
  private Long id;
  @Column(name = "name", nullable = false, length = 100)
  private String name;
  @Column(name = "cnpj", nullable = false, length = 20, unique = true)
  private String cnpj;
  @Column(name = "email", nullable = false, length = 50, unique = true)
  private String email;
  @Column(name = "login", nullable = false, length = 50, unique = true)
  private String login;
  @JsonIgnore
  @Column(name = "password", nullable = false, length = 255)
  private String password;
  @Column(name = "last_update")
    private LocalDateTime lastUpdate;
    // relacionamento entre tabelas Restaurante tem 1 endereco - one - to - one
    @OneToOne(optional = false)
    @JoinColumn(
            name = "address_id",
            nullable = false,
            unique = true,
            foreignKey = @ForeignKey(name = "fk_rest_address")
    )
    private AddressEntity address;
    // roda vai grava a data
    @PrePersist
    // roda vai atualizar a data
    @PreUpdate
    private void updateTimestamp() {
        this.lastUpdate = LocalDateTime.now();
    }

    public Long getId() {
      return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
      return name;
    }

    public void setName(String name){
      this.name = name;
     }

    public String getEmail(){
      return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getCnpj (){
    return cnpj;
    }

    public void setCnpj (String cnpj){
      this.cnpj = cnpj;
    }

    public String getLogin(){
      return  login;
    }

    public void setLogin (String login){
    this.login = login;
    }
    /// getter necessário para JPA / autenticação
    public String getPassword() {
      return password;
    }

    // senha ja deve chegar criptografada pelo Service
    public void setPassword(String password) {
      this.password = password;
    }

    public LocalDateTime getLastUpdate(){
      return   lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

   public AddressEntity getAddress() {
     return this.address;
  }

  public void setAddress(AddressEntity savedAddress) {
    this.address = savedAddress;
  }
}
