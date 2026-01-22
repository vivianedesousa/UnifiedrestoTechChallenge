
package com.unifiedresto.platform.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unifiedresto.platform.dto.*;
import com.unifiedresto.platform.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.ArrayList;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
public class CustomerControllerTest {
       private MockMvc mockMvc;
        private CustomerService customerService;
        private ObjectMapper objectMapper;
        @BeforeEach
        void setup() {
            customerService = Mockito.mock(CustomerService.class);
            CustomerController controller = new CustomerController(customerService);
            mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
            objectMapper = new ObjectMapper();
        }

        // =========================
        // CREATE CUSTOMER
        // =========================
        @Test
        void createCustomer() throws Exception {

            CustomerRequestDTO request = new CustomerRequestDTO();
            request.setName("viviane");
            request.setEmail("viviane@email.com");

            CustomerResponseDTO response = new CustomerResponseDTO();
            response.setName("viviane");
            response.setEmail("viviane@email.com");

            Mockito.when(customerService.create(Mockito.any(CustomerRequestDTO.class)))
                    .thenReturn(response);

            mockMvc.perform(post("/api/v1/customers")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isCreated());
        }

        // =========================
        // UPDATE CUSTOMER
        // =========================
        @Test
        void updateCustomer() throws Exception {

            CustomerRequestDTO request = new CustomerRequestDTO();
            request.setName("Renata");

            CustomerResponseDTO response = new CustomerResponseDTO();
            response.setName("renata");

            Mockito.when(customerService.update(Mockito.eq(1L), Mockito.any()))
                    .thenReturn(response);

            mockMvc.perform(put("/api/v1/customers/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk());
        }

        // =========================
        // LOGIN
        // =========================
        @Test
        void loginCustomer() throws Exception {
            LoginRequestDTO request = new LoginRequestDTO();
            request.setLogin("viviane");
            request.setPassword("568");
            CustomerResponseDTO response = new CustomerResponseDTO();
            response.setLogin("viviane");

            Mockito.when(customerService.login("viviane", "568"))
                    .thenReturn(response);
            mockMvc.perform(post("/api/v1/customers/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk());
        }
    // =========================
    // FIND ALL
// =========================
    @Test
    void findAllCustomers() throws Exception {
        List<CustomerDetailResponseDTO> list = new ArrayList<>();
        list.add(new CustomerDetailResponseDTO());
        Mockito.when(customerService.findAll()).thenReturn(list);
        mockMvc.perform(get("/api/v1/customers"))
                .andExpect(status().isOk());
    }
        // =========================
        // SEARCH BY NAME
        // =========================
        @Test
        void searchCustomerByName() throws Exception {
            List<CustomerResponseDTO> list = new ArrayList<>();
            list.add(new CustomerResponseDTO());
            Mockito.when(customerService.searchByName("viviane"))
                    .thenReturn(list);
            mockMvc.perform(get("/api/v1/customers/search")
                            .param("name", "Ana"))
                    .andExpect(status().isOk());
        }
        // =========================
        // UPDATE PASSWORD
        // =========================
        @Test
        void updatePassword() throws Exception {
            PasswordRequestDTO request = new PasswordRequestDTO();
            request.setCurrentPassword("1234568");     // senha atual (obrigatório)
            request.setPassword("0987##$%%");      // nova senha
            Mockito.doNothing()
                    .when(customerService)
                    .updatePassword(Mockito.eq(1L), Mockito.any(PasswordRequestDTO.class));
            mockMvc.perform(patch("/api/v1/customers/1/password")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isNoContent());
        }
    // =========================
        // DELETE
        // =========================
        @Test
        void deleteCustomer() throws Exception {
            Mockito.doNothing().when(customerService).delete(1L);
            mockMvc.perform(delete("/api/v1/customers/1"))
            .andExpect(status().isNoContent());
     }
 }


