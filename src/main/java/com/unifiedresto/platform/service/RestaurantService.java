package com.unifiedresto.platform.service;
import com.unifiedresto.platform.dto.CustomerRequestDTO;
import com.unifiedresto.platform.dto.PasswordRequestDTO;
import com.unifiedresto.platform.dto.RestaurantRequestDTO;
import com.unifiedresto.platform.dto.RestaurantResponseDTO;
import com.unifiedresto.platform.dto.RestaurantDetailResponseDTO;
import java.util.List;

public interface RestaurantService {
  // RestaurantEntity create(RestaurantRequestDTO dto);
 // void updatePassword(Long id, PasswordRequestDTO dto);
  // RestaurantResponseDTO login(String login, String password);
//   // feito atualizar sem senha
 //  RestaurantEntity update(Long id, RestaurantRequestDTO dto);
 //  List<RestaurantEntity> searchByName(String name);
 // List<RestaurantEntity> findAll();
  // void delete (long id);


 //  A SERVICE DEFINE OQUE A API DEVOLVE
   RestaurantResponseDTO create(RestaurantRequestDTO dto);
   RestaurantResponseDTO update(Long id, RestaurantRequestDTO dto);
   RestaurantResponseDTO login(String login, String password);
   List<RestaurantResponseDTO> searchByName(String name);
   List<RestaurantDetailResponseDTO> findAll();
   void updatePassword(Long id, PasswordRequestDTO dto);
   void delete(long id);
}









//    RestaurantEntity create(RestaurantEntity restaurant);
//    RestaurantEntity update(Long id, RestaurantEntity restaurant);
//    void updatePassword(Long id, String newPassword);
//    void delete(Long id);
//    RestaurantEntity login(String login, String password);
//    List<RestaurantEntity> searchByName(String name);
//    List<RestaurantEntity> findAll();
