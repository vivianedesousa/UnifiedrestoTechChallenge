package com.unifiedresto.platform.service;
import com.unifiedresto.platform.dto.PasswordRequestDTO;
import com.unifiedresto.platform.dto.RestaurantDetailResponseDTO;
import com.unifiedresto.platform.exception.ResourceNotFoundException;
import com.unifiedresto.platform.dto.RestaurantRequestDTO;
import com.unifiedresto.platform.dto.RestaurantResponseDTO;
import com.unifiedresto.platform.exception.*;
import com.unifiedresto.platform.model.RestaurantEntity;
import com.unifiedresto.platform.repository.AddressRepository;
import com.unifiedresto.platform.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

   public class RestaurantServiceImplTest {
       private RestaurantRepository restaurantRepository;
       private AddressRepository addressRepository;
       private RestaurantServiceImpl service;

       @BeforeEach
       void setup() {
           restaurantRepository = Mockito.mock(RestaurantRepository.class);
           addressRepository = Mockito.mock(AddressRepository.class);
           service = new RestaurantServiceImpl(restaurantRepository, addressRepository);
       }

       // =========================
       // CREATE - EMAIL DUPLICADO
       // =========================
       @Test
       void createRestaurant_emailAlreadyExists() {
           RestaurantRequestDTO dto = new RestaurantRequestDTO();
           dto.setEmail("ana@email.com");

           Mockito.when(restaurantRepository.existsByEmail("ana@email.com"))
                   .thenReturn(true);
           try {
               service.create(dto);
               fail("Expected EmailAlreadyExistsException");
           } catch (EmailAlreadyExistsException e) {
               assertTrue(true);
           }
       }

       // =========================
       // CREATE - LOGIN DUPLICADO
       // =========================
       @Test
       void createRestaurant_loginAlreadyExists() {
           RestaurantRequestDTO dto = new RestaurantRequestDTO();
           dto.setEmail("viviane@email.com");
           dto.setLogin("viviane");

           Mockito.when(restaurantRepository.existsByEmail("viviane@email.com"))
                   .thenReturn(false);
           Mockito.when(restaurantRepository.existsByLogin("viviane"))
                   .thenReturn(true);

           try {
               service.create(dto);
               fail("Expected LoginAlreadyExistsException");
           } catch (LoginAlreadyExistsException e) {
               assertTrue(true);
           }
       }

       // =========================
       // CREATE - CNPJ DUPLICADO
       // =========================
       @Test
       void createRestaurant_cnpjAlreadyExists() {
           RestaurantRequestDTO dto = new RestaurantRequestDTO();
           dto.setEmail("viviane@email.com");
           dto.setLogin("viviane");
           dto.setCnpj("12378");

           Mockito.when(restaurantRepository.existsByEmail("viviane@email.com"))
                   .thenReturn(false);
           Mockito.when(restaurantRepository.existsByLogin("viviane"))
                   .thenReturn(false);
           Mockito.when(restaurantRepository.existsByCnpj("12378"))
                   .thenReturn(true);
           try {
               service.create(dto);
               fail("Expected CnpjAlreadyExistsException");
           } catch (CnpjAlreadyExistsException e) {
               assertTrue(true);
           }
       }

       // =========================
       // UPDATE - NOT FOUND
       // =========================
       @Test
       void updateRestaurant_notFound() {
           Mockito.when(restaurantRepository.findById(1L))
                   .thenReturn(Optional.empty());
           RestaurantRequestDTO dto = new RestaurantRequestDTO();
           try {
               service.update(1L, dto);
               fail("Expected ResourceNotFoundException");
           } catch (ResourceNotFoundException e) {
               assertTrue(true);
           }
       }

       // =========================
       // LOGIN - INVALID
       // =========================
       @Test
       void login_invalidCredentials() {
           Mockito.when(
                   restaurantRepository.findByLoginAndPassword("admin", "123")
           ).thenReturn(Optional.empty());
           try {
               service.login("admin", "123");
               fail("Expected InvalidLoginException");
           } catch (InvalidLoginException e) {
               assertTrue(true);
           }
       }
       @Test
       void updatePassword_success() {
           RestaurantEntity restaurant = new RestaurantEntity();
           restaurant.setId(1L);
           restaurant.setPassword("123456"); // senha atual no banco
           PasswordRequestDTO dto = new PasswordRequestDTO();
           dto.setCurrentPassword("123456");     // senha correta
           dto.setPassword("newPassword");       // nova senha
           Mockito.when(restaurantRepository.findById(1L))
                   .thenReturn(Optional.of(restaurant));
           service.updatePassword(1L, dto);
           // assert
           Mockito.verify(restaurantRepository).save(restaurant);
           assertEquals("newPassword", restaurant.getPassword());
       }
       // =========================
       // SEARCH BY NAME
       // =========================
       @Test
       void searchRestaurantByName() {
           List<RestaurantEntity> list = new ArrayList<>();
           RestaurantEntity restaurant = new RestaurantEntity();
           restaurant.setName("Pizza House");
           list.add(restaurant);

           Mockito.when(
                   restaurantRepository.findByNameContainingIgnoreCase("Pizza")
           ).thenReturn(list);

           List<RestaurantResponseDTO> result =
                   service.searchByName("Pizza");

           assertEquals(1, result.size());
       }

       // =========================
       // FIND ALL
       // =========================
       @Test
       void findAllRestaurants() {
           List<RestaurantEntity> list = new ArrayList<>();
           RestaurantEntity restaurant = new RestaurantEntity();
           restaurant.setId(1L);
           restaurant.setLastUpdate(LocalDateTime.now());
           list.add(restaurant);

           Mockito.when(restaurantRepository.findAll())
                   .thenReturn(list);

           List<RestaurantDetailResponseDTO> result =
                   service.findAll();

           assertEquals(1, result.size());
       }

       // =========================
       // DELETE - NOT FOUND
       // =========================
       @Test
       void deleteRestaurant_notFound() {
           Mockito.when(restaurantRepository.existsById(1L))
                   .thenReturn(false);
           try {
               service.delete(1);
               fail("Expected ResourceNotFoundException");
           } catch (ResourceNotFoundException e) {
               assertTrue(true);
           }
       }
   }

