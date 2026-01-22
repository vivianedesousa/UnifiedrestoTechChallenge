package com.unifiedresto.platform.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unifiedresto.platform.dto.*;
import com.unifiedresto.platform.handler.GlobalExceptionHandler;
import com.unifiedresto.platform.service.RestaurantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.ArrayList;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RestaurantControllerTest {
    private MockMvc mockMvc;
    private RestaurantService restaurantService;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        restaurantService = Mockito.mock(RestaurantService.class);
        RestaurantController controller =
                new RestaurantController(restaurantService);

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())// ESSENCIAL
                .build();

        objectMapper = new ObjectMapper();
    }

    // =========================
    // CREATE RESTAURANT
    // =========================
    @Test
    void createRestaurant() throws Exception {
        RestaurantRequestDTO request = new RestaurantRequestDTO();
        request.setName("Sample Dinner");
        request.setEmail("diner@email.com");
        RestaurantResponseDTO response = new RestaurantResponseDTO();
        response.setName("sample Diner");
        response.setEmail("diner@email.com");
        Mockito.when(restaurantService.create(Mockito.any(RestaurantRequestDTO.class)))
                .thenReturn(response);
        mockMvc.perform(post("/api/v1/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    // =========================
    // UPDATE RESTAURANT
    // =========================
    @Test
    void updateRestaurant() throws Exception {

        RestaurantRequestDTO request = new RestaurantRequestDTO();
        request.setName("Updated Restaurant");
        RestaurantResponseDTO response = new RestaurantResponseDTO();
        response.setName("Updated Restaurant");
        Mockito.when(restaurantService.update(Mockito.eq(1L), Mockito.any()))
                .thenReturn(response);

        mockMvc.perform(put("/api/v1/restaurants/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    // =========================
    // LOGIN
    // =========================
    @Test
    void loginRestaurant() throws Exception {
        LoginRequestDTO request = new LoginRequestDTO();
        request.setLogin("manager");
        request.setPassword("ekhfd@34");
        RestaurantResponseDTO response = new RestaurantResponseDTO();
        response.setLogin("manager");

        Mockito.when(restaurantService.login("manager", "ekhfd@34"))
                .thenReturn(response);

        mockMvc.perform(post("/api/v1/restaurants/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
    // =========================
    // FIND ALL
    // =========================
    @Test
    void findAllRestaurants() throws Exception {

        List<RestaurantDetailResponseDTO> list = new ArrayList<>();
        list.add(new RestaurantDetailResponseDTO());

        Mockito.when(restaurantService.findAll())
                .thenReturn(list);

        mockMvc.perform(get("/api/v1/restaurants"))
                .andExpect(status().isOk());
    }
    // =========================
    // SEARCH BY NAME
    // =========================
    @Test
    void searchRestaurantByName() throws Exception {

        List<RestaurantResponseDTO> list = new ArrayList<>();
        list.add(new RestaurantResponseDTO());

        Mockito.when(restaurantService.searchByName("Grill House"))
                .thenReturn(list);

        mockMvc.perform(get("/api/v1/restaurants/search")
                        .param("name", "Grill House"))
                .andExpect(status().isOk());
    }
    // =========================
    // UPDATE PASSWORD
    // =========================
    @Test
    void updatePassword() throws Exception {
        PasswordRequestDTO request = new PasswordRequestDTO();
        request.setCurrentPassword("1234560");     // senha atual
        request.setPassword("309847$%");      // nova senha

        Mockito.doNothing()
                .when(restaurantService)   // ✅ SERVICE CORRETO
                .updatePassword(Mockito.eq(1L), Mockito.any(PasswordRequestDTO.class));

        mockMvc.perform(patch("/api/v1/restaurants/1/password")   // ✅ URL CORRETA
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());
    }

    // =========================
    // DELETE
    // =========================
    @Test
    void deleteRestaurant() throws Exception {
        Mockito.doNothing()
                .when(restaurantService)
                .delete(1L);
        mockMvc.perform(delete("/api/v1/restaurants/1"))
                .andExpect(status().isNoContent());
    }
}
