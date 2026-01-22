package com.unifiedresto.platform.service;
import com.unifiedresto.platform.dto.*;
import com.unifiedresto.platform.model.CustomerEntity;
import java.util.List;
  //contrato com regras
  public interface CustomerService {
     // “A interface define o contrato da camada de serviço, garantindo que a implementação
       // /respeite as regras de negócio e o formato de resposta da A
      // CREATE
      CustomerResponseDTO create(CustomerRequestDTO dto);
      // UPDATE (SEM SENHA)
      CustomerResponseDTO update(Long id, CustomerRequestDTO dto);
      // LOGIN
      CustomerResponseDTO login(String login, String password);
      // SEARCH BY NAME
      // usa lista de DTO SIMPLES
      List<CustomerResponseDTO> searchByName(String name); // // lista simples
      // FIND ALL USA LISTA DE dto completa
      List<CustomerDetailResponseDTO> findAll();
      // lista detalhada
   /////   List<CustomerDetailResponseDTO> findAll();
      // UPDATE PASSWORD (endpoint separado)
      void updatePassword(Long id, PasswordRequestDTO dto);
      // DELETE
      void delete(long id);
  }






//    //Cadastro de usuário
//    CustomerEntity create(CustomerRequestDTO dto);
//  // troca de senha em enpoint separado diretamente dentro db (requistos) parcial metod PATH
//  void updatePassword(Long id, PasswordRequestDTO dto);
//  // fazer login
//    CustomerResponseDTO login(String login, String password);
//    // bucar por nome  regras de negocios
//    List<CustomerEntity> searchByName(String name);
//    // Listagem geral
//    List<CustomerEntity> findAll();
//    //Atualizaçao de dados (exceto senha) // sem senha
//    CustomerEntity update(Long id, CustomerRequestDTO dto);