package com.unifiedresto.platform.service;
import com.unifiedresto.platform.exception.*;
import com.unifiedresto.platform.dto.*;
import com.unifiedresto.platform.model.AddressEntity;
import com.unifiedresto.platform.repository.AddressRepository;
import com.unifiedresto.platform.repository.CustomerRepository;
import com.unifiedresto.platform.model.CustomerEntity;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    public final CustomerRepository customerRepository;
    public final AddressRepository addressRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository, AddressRepository addressRepository) {
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    @Transactional // @Transactional faz commit NO DB
    public CustomerResponseDTO create(CustomerRequestDTO dto) {
        if (customerRepository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyExistsException(); // lancando um aexercao
        }
        if (customerRepository.existsByLogin(dto.getLogin())) {
            throw new LoginAlreadyExistsException();
        }
        if (customerRepository.existsByCpf(dto.getCpf())) {
            throw new CpfAlreadyExistsException();
        }
        AddressEntity address = new AddressEntity();
        address.setStreet(dto.getAddress().getStreet());
        address.setNumber(dto.getAddress().getNumber());
        address.setCity(dto.getAddress().getCity());
        address.setPostalCode(dto.getAddress().getPostalCode());
        addressRepository.save(address);

        CustomerEntity customer = new CustomerEntity();
        customer.setName(dto.getName());
        customer.setCpf(dto.getCpf());
        customer.setEmail(dto.getEmail());
        customer.setLogin(dto.getLogin());
        customer.setPassword(dto.getPassword());
        customer.setAddress(address);

        CustomerEntity saved = customerRepository.save(customer);
        return toResponse(saved);
    }

 /// manda para git mudanca
    // usa este texto feat: ajustes na atualização de clientes e restaurantes com validações
    @Override
    @Transactional
    public CustomerResponseDTO update(Long id, CustomerRequestDTO dto) {
        Optional<CustomerEntity> optionalCustomer =
                customerRepository.findById(id);

        if (optionalCustomer.isEmpty()) {
            throw new ResourceNotFoundException("Customer not found");
        }

        CustomerEntity customer = optionalCustomer.get();

        // valida email único
        if (customerRepository.existsByEmail(dto.getEmail())
                && !customer.getEmail().equals(dto.getEmail())) {
            throw new EmailAlreadyExistsException();
        }

        // valida login único
        if (customerRepository.existsByLogin(dto.getLogin())
                && !customer.getLogin().equals(dto.getLogin())) {
            throw new LoginAlreadyExistsException();
        }

        // atualiza dados básicos
        customer.setEmail(dto.getEmail());
        customer.setLogin(dto.getLogin());

        // atualiza endereço
        if (customer.getAddress() != null && dto.getAddress() != null) {
            AddressEntity address = customer.getAddress();
            address.setStreet(dto.getAddress().getStreet());
            address.setNumber(dto.getAddress().getNumber());
            address.setCity(dto.getAddress().getCity());
            address.setPostalCode(dto.getAddress().getPostalCode());
        }

        customer.setLastUpdate(LocalDateTime.now());

        return toResponse(customerRepository.save(customer));
    }


    @Override
    @Transactional
    public CustomerResponseDTO login(String login, String password) {
        Optional<CustomerEntity> optional =
               customerRepository.findByLoginAndPassword(login, password);
        if (optional.isEmpty()) {
             throw new InvalidLoginException("Invalid login or password");
        }

        CustomerEntity customer = optional.get();
        return toResponse(customer);
    }

    @Override
    @Transactional
    public List<CustomerResponseDTO> searchByName(String name) {
        List<CustomerEntity> customers =
                customerRepository.findByNameContainingIgnoreCase(name);
        List<CustomerResponseDTO> responseList = new ArrayList<>();
        for (CustomerEntity customer : customers) {
            responseList.add(toResponse(customer));
        }
        return responseList;
    }

    @Override
    @Transactional
    public List<CustomerDetailResponseDTO> findAll() {
            //  Busca todos os clientes no banco
            List<CustomerEntity> customers = customerRepository.findAll();
            // Cria a lista de resposta
            List<CustomerDetailResponseDTO> responseList = new ArrayList<>();
            //  Converte Entity -> DetailResponseDTO
            for (CustomerEntity customer : customers) {
                responseList.add(toDetailResponse(customer));
            }
            // Retorna a lista detalhada
            return responseList;
    }
          //feita atualizcao que esta faltando  usa metodo save para persit no db
    @Override
    public void updatePassword(Long id, PasswordRequestDTO dto) {
        Optional<CustomerEntity> optional = customerRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ResourceNotFoundException("Customer not found");
        }
        CustomerEntity customer = optional.get();
        // valida a senha
        if (!customer.getPassword().equals(dto.getCurrentPassword())) {
            throw new InvalidPasswordException();
        }
        // troca de senha
        customer.setPassword(dto.getPassword());
        customer.setLastUpdate(LocalDateTime.now());
        customerRepository.save(customer);
    }

    @Override
    @Transactional
    public void delete(long id) {
        if (!customerRepository.existsById(id)) {
            throw  new ResourceNotFoundException("Restaurant not found");
        } else {
            customerRepository.deleteById(id);
        }
    }

     // ENTITY -> DTO
    private CustomerResponseDTO toResponse(CustomerEntity customer) {
        CustomerResponseDTO dto = new CustomerResponseDTO();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setEmail(customer.getEmail());
        dto.setLogin(customer.getLogin());
        dto.setLastUpdate(customer.getLastUpdate()); // feito mudancao nao retorno
        return dto;
    }

    private CustomerDetailResponseDTO toDetailResponse(CustomerEntity customer) {
        CustomerDetailResponseDTO dto = new CustomerDetailResponseDTO();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setCpf(customer.getCpf());
        dto.setEmail(customer.getEmail());
        dto.setLogin(customer.getLogin());
        dto.setLastUpdate(customer.getLastUpdate());

         // se existe risco de cliente sem endereco
         if(customer.getAddress()!= null) {
             AddressResponseDTO addressDTO = new AddressResponseDTO();
             addressDTO.setStreet(customer.getAddress().getStreet());
             addressDTO.setNumber(customer.getAddress().getNumber());
             addressDTO.setCity(customer.getAddress().getCity());
             addressDTO.setPostalCode(customer.getAddress().getPostalCode());
             dto.setAddress(addressDTO);
         }
        return dto;
  }
}


