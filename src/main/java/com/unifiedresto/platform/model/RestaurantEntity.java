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
    // Evita dependência de banco  Ciclo de vida de uma entidade (simplificado)
    //NEW → PERSIST → MANAGED → UPDATE → REMOVE
    /**
     * Atualiza o campo {@code lastUpdate} antes de operações de INSERT e UPDATE,
     * utilizando callbacks JPA para evitar dependência de triggers no banco.
     oda vez que o registro for alterado, a data deve ser atualizada
     */
    @PrePersist // roda vai grava a data
    @PreUpdate// roda vai atualizar a data
    private void updateTimestamp() {
        this.lastUpdate = LocalDateTime.now();
    }
    // Só muda o comportamento dos objetos NA MEMÓRIA
    //Em JPA, equals e hashCode devem ser baseados no ID para evitar inconsistências e bugs
    // eles definem o comportamento correto das entidades  na memoria

    // asc
//    @Column(name = "order_number", nullable = false)
//    private Integer orderNumber;
//
//   // Getter (obrigatório para JSON)
//   public Integer getOrderNumber() {
//     return orderNumber;
//   }

  //  Setter (opcional)
  //  public void setOrderNumber(Integer orderNumber) {
  //      this.orderNumber = orderNumber;
  //  }
    // getters and setters

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

   //public void setPassword(String hashedPassword) {
  //this.password = hashedPassword;
  //}

    public LocalDateTime getLastUpdate(){
      return   lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    // este dois ultimos getters and setters vai permite criar um endereco assciado a um endereco do restaurante
   public AddressEntity getAddress() {
     return this.address;
  }

  public void setAddress(AddressEntity savedAddress) {
    this.address = savedAddress;
  }
}
