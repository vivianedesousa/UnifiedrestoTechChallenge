package com.unifiedresto.platform.repository;
import com.unifiedresto.platform.model.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long> {
   //Qual a relação do Hibernate com esse código?
    //Aqui está a ligação com o Hibernate.
    //JpaRepository faz parte do Spring Data JPA
    //Spring Data JPA usa a JPA
    //Quem implementa a JPA por baixo é o Hibernate
  // lendo o comando  (findByLogin)
 // Cria a consulta automaticamente
///customerRepository.findByLogin("joao");
  // Spring:
   // Lê o nome do método (findByLogin)
  //  Cria a consulta automaticamente
  //  Usa a JPA
  //  O Hibernate gera o SQL
   // Executa no banco

    // Validação de e-mail único
    boolean existsByEmail(String email); //ibernate SELECT COUNT

    // Validação de login único
    boolean existsByLogin(String login);

    // Validação de CNPJ único
    boolean existsByCnpj(String cnpj);

    // Login simples (conforme requisito do trabalho) // fala com o banco
    Optional<RestaurantEntity> findByLoginAndPassword(String login, String password);
    // Busca por nome (case insensitive)
    Optional<RestaurantEntity> findById(Long id);
    List<RestaurantEntity> findByNameContainingIgnoreCase(String name);
}


  //Garantia de que o e-mail cadastrado seja único
 // boolean existsByEmail(String email); //  Hibernate SELECT COUNT
  // Validação de login obrigatória
  //boolean existsByLogin(String login);//SELECT COUNT
    //Cadastro de usuários (dados válidos)
  //boolean existsByCnpj(String cnpj); //SELECT
  //Validação de login obrigatória, consultando dados no banco
 // Optional<RestaurantEntity> findByLogin(String login);
    // vai fazer um SELECT * FROM NAME E ENCONTRA E RETORNA DENTRO DE UMA LISTA
    // Busca de usuários pelo nome certo
  // ultimo feito com asc
    //    List<RestaurantEntity> findByNameContainingIgnoreCaseOrderByOrderNumberAsc(String name);
   //  List<RestaurantEntity> findAllByOrderByOrderNumberAsc();
//  Optional<RestaurantEntity> findByLoginAndPassword(String login, String password);
//   List<RestaurantEntity> findAll();
//   List<RestaurantEntity> findByNameContainingIgnoreCase(String name);

