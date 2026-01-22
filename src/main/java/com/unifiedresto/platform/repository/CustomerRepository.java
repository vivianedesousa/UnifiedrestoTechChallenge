package com.unifiedresto.platform.repository;
import com.unifiedresto.platform.model.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
@Repository
//Essa linha 9 descrita aqui embaixo ta descrevendo o Spring Data  Jpa
//O repository usa JPA
public interface CustomerRepository extends JpaRepository<CustomerEntity,Long> {

    boolean existsByEmail(String email);

    boolean existsByLogin(String login);

    boolean existsByCpf(String cpf);
    Optional<CustomerEntity> findByLoginAndPassword(String login, String password);
    Optional<CustomerEntity> findByLogin(String login);
    List<CustomerEntity> findByNameContainingIgnoreCase(String name);
}
