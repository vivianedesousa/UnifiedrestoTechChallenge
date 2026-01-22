package com.unifiedresto.platform.repository;
import com.unifiedresto.platform.model.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
  //porque este esta interface  esta vazia sem metodos ?
   // “Porque nao existe caso de uso que exija busca isolada de endereço.”
  //o endereco nao sera buscado sozinho ,sempre vem junto de cleinte or restaurante
  //elogo nao existirar endpoint/address (e nem dev Não existe endpoint /addresses (e nem dev


}
