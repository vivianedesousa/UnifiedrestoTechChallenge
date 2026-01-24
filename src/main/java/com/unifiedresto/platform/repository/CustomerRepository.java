package com.unifiedresto.platform.repository;
import com.unifiedresto.platform.model.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
@Repository

public interface CustomerRepository extends JpaRepository<CustomerEntity,Long> {
    // Validações de unicidade
    boolean existsByEmail(String email);
    boolean existsByLogin(String login);
    boolean existsByCpf(String cpf);

    //Consulta
    Optional<CustomerEntity> findByLoginAndPassword(String login, String password);
    Optional<CustomerEntity> findByLogin(String login);
    List<CustomerEntity> findByNameContainingIgnoreCase(String name);
}
