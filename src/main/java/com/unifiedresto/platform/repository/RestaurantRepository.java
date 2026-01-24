package com.unifiedresto.platform.repository;
import com.unifiedresto.platform.model.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long> {
   // Validações de unicidade
  boolean existsByEmail(String email);
  boolean existsByLogin(String login);
  boolean existsByCnpj(String cnpj);

   // Consulta
  Optional<RestaurantEntity> findByLoginAndPassword(String login, String password);
  Optional<RestaurantEntity> findById(Long id);
  List<RestaurantEntity> findByNameContainingIgnoreCase(String name);
}
