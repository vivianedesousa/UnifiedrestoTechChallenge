package com.unifiedresto.platform.service;
import com.unifiedresto.platform.dto.PasswordRequestDTO;
import com.unifiedresto.platform.dto.RestaurantRequestDTO;
import com.unifiedresto.platform.dto.RestaurantResponseDTO;
import com.unifiedresto.platform.dto.RestaurantDetailResponseDTO;
import java.util.List;

public interface RestaurantService {
   RestaurantResponseDTO create(RestaurantRequestDTO dto);
   RestaurantResponseDTO update(Long id, RestaurantRequestDTO dto);
   RestaurantResponseDTO login(String login, String password);
   List<RestaurantResponseDTO> searchByName(String name);
   List<RestaurantDetailResponseDTO> findAll();
   void updatePassword(Long id, PasswordRequestDTO dto);
   void delete(long id);
}

