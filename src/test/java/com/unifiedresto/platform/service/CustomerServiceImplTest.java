package com.unifiedresto.platform.service;
import com.unifiedresto.platform.dto.*;
import com.unifiedresto.platform.exception.*;
import com.unifiedresto.platform.model.AddressEntity;
import com.unifiedresto.platform.model.CustomerEntity;
import com.unifiedresto.platform.repository.AddressRepository;
import com.unifiedresto.platform.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
public class CustomerServiceImplTest {
    private CustomerRepository customerRepository;
    private AddressRepository addressRepository;
    private CustomerServiceImpl service;
    @BeforeEach
    void setup() {
        customerRepository = Mockito.mock(CustomerRepository.class);
        addressRepository = Mockito.mock(AddressRepository.class);
        service = new CustomerServiceImpl(customerRepository, addressRepository);
    }

    // =========================
    // CREATE - EMAIL DUPLICADO
    // =========================
    @Test
    void createCustomer_emailAlreadyExists() {
        CustomerRequestDTO dto = new CustomerRequestDTO();
        dto.setEmail("test@email.com");
        Mockito.when(customerRepository.existsByEmail("test@email.com"))
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
    void createCustomer_loginAlreadyExists() {
        CustomerRequestDTO dto = new CustomerRequestDTO();
        dto.setEmail("ok@email.com");
        dto.setLogin("login");
        Mockito.when(customerRepository.existsByEmail("ok@email.com"))
                .thenReturn(false);
        Mockito.when(customerRepository.existsByLogin("login"))
                .thenReturn(true);

        try {
            service.create(dto);
            fail("Expected LoginAlreadyExistsException");
        } catch (LoginAlreadyExistsException e) {
            assertTrue(true);
        }
    }

    // =========================
    // CREATE - CPF DUPLICADO
    // =========================
    @Test
    void createCustomer_cpfAlreadyExists() {

        CustomerRequestDTO dto = new CustomerRequestDTO();
        dto.setEmail("ok@email.com");
        dto.setLogin("login");
        dto.setCpf("123");
        Mockito.when(customerRepository.existsByEmail("ok@email.com"))
                .thenReturn(false);
        Mockito.when(customerRepository.existsByLogin("login"))
                .thenReturn(false);
        Mockito.when(customerRepository.existsByCpf("123"))
                .thenReturn(true);

        try {
            service.create(dto);
            fail("Expected CpfAlreadyExistsException");
        } catch (CpfAlreadyExistsException e) {
            assertTrue(true);
        }
    }
    // =========================
    // UPDATE - NOT FOUND
    // =========================
    @Test
    void updateCustomer_notFound() {
        Mockito.when(customerRepository.findById(1L))
                .thenReturn(Optional.empty());

        CustomerRequestDTO dto = new CustomerRequestDTO();

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
                customerRepository.findByLoginAndPassword("admin", "123")
        ).thenReturn(Optional.empty());

        try {
            service.login("admin", "123");
            fail("Expected InvalidLoginException");
        } catch (InvalidLoginException e) {
            assertTrue(true);
        }
    }
    // =========================
    // SEARCH BY NAME
    // =========================
    @Test
    void searchCustomerByName() {

        List<CustomerEntity> list = new ArrayList<>();

        CustomerEntity customer = new CustomerEntity();
        customer.setName("João");

        list.add(customer);

        Mockito.when(
                customerRepository.findByNameContainingIgnoreCase("Jo")
        ).thenReturn(list);

        List<CustomerResponseDTO> result =
                service.searchByName("Jo");

        assertEquals(1, result.size());
        assertEquals("João", result.get(0).getName());
    }
    // =========================
    // FIND ALL
    // =========================
    @Test
    void findAllCustomers() {

        List<CustomerEntity> list = new ArrayList<>();

        CustomerEntity customer = new CustomerEntity();
        customer.setId(1L);
        customer.setName("Maria");
        customer.setCpf("111");
        customer.setEmail("maria@email.com");
        customer.setLogin("maria");
        customer.setLastUpdate(LocalDateTime.now());

        AddressEntity address = new AddressEntity();
        address.setStreet("Rua A");
        address.setNumber("10");
        address.setCity("São Paulo");
        address.setPostalCode("00000-000");
        customer.setAddress(address);
        list.add(customer);
        Mockito.when(customerRepository.findAll())
                .thenReturn(list);

        List<CustomerDetailResponseDTO> result =
                service.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Maria", result.get(0).getName());
        assertEquals("São Paulo", result.get(0).getAddress().getCity());
    }

    // =========================
    // DELETE - NOT FOUND
    // =========================
    @Test
    void deleteCustomer_notFound() {
        Mockito.when(customerRepository.existsById(1L))
                .thenReturn(false);
        try {
            service.delete(1L);
            fail("Expected ResourceNotFoundException");
        } catch (ResourceNotFoundException e) {
            assertTrue(true);
        }
    }
}

