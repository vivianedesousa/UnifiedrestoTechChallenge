package com.unifiedresto.platform.service;
import com.unifiedresto.platform.dto.*;
import com.unifiedresto.platform.exception.*;
import com.unifiedresto.platform.model.AddressEntity;
import com.unifiedresto.platform.model.RestaurantEntity;
import com.unifiedresto.platform.repository.AddressRepository;
import com.unifiedresto.platform.repository.RestaurantRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
  //esta classe esta implementando o contrato interface Restaurante service com as regras
public class RestaurantServiceImpl implements RestaurantService {
  private final RestaurantRepository restaurantRepository;
  private final AddressRepository addressRepository;
  public RestaurantServiceImpl(RestaurantRepository restaurantRepository,
          AddressRepository addressRepository) {
     this.restaurantRepository = restaurantRepository;
     this.addressRepository = addressRepository;
  }

    @Override
    @Transactional
    public RestaurantResponseDTO create(RestaurantRequestDTO dto) {
        //validações simples de acordo com requistos
        if (restaurantRepository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyExistsException();
        }

        if (restaurantRepository.existsByLogin(dto.getLogin())) {
            throw new LoginAlreadyExistsException();
        }

        if (restaurantRepository.existsByCnpj(dto.getCnpj())) {
            throw new CnpjAlreadyExistsException();
        }

        AddressEntity address = new AddressEntity();
        address.setStreet(dto.getAddress().getStreet());
        address.setNumber(dto.getAddress().getNumber());
        address.setCity(dto.getAddress().getCity());
        address.setPostalCode(dto.getAddress().getPostalCode());
        addressRepository.save(address);

        RestaurantEntity restaurant = new RestaurantEntity();
        restaurant.setName(dto.getName());
        restaurant.setCnpj(dto.getCnpj());
        restaurant.setEmail(dto.getEmail());
        restaurant.setLogin(dto.getLogin());
        restaurant.setPassword(dto.getPassword());
        restaurant.setAddress(address);

        RestaurantEntity saved = restaurantRepository.save(restaurant);

        // uso do toResponse
        return toResponse(saved);
    }
    // envair mudas para o git
    // // usa este texto feat: ajustes na atualização de clientes e restaurantes com validações
    @Override
    @Transactional
    public RestaurantResponseDTO update(Long id, RestaurantRequestDTO dto) {

        Optional<RestaurantEntity> optionalRestaurant =
                restaurantRepository.findById(id);

        if (optionalRestaurant.isEmpty()) {
            throw new ResourceNotFoundException("Restaurant not found");
        }

        RestaurantEntity restaurant = optionalRestaurant.get();

        // valida email único
        if (restaurantRepository.existsByEmail(dto.getEmail())
                && !restaurant.getEmail().equals(dto.getEmail())) {
            throw new EmailAlreadyExistsException();
        }

        // valida login único
        if (restaurantRepository.existsByLogin(dto.getLogin())
                && !restaurant.getLogin().equals(dto.getLogin())) {
            throw new LoginAlreadyExistsException();
        }

        // atualiza dados básicos
        restaurant.setName(dto.getName());
        restaurant.setEmail(dto.getEmail());
        restaurant.setLogin(dto.getLogin());

        // atualiza endereço (com validação de null)
        if (restaurant.getAddress() != null && dto.getAddress() != null) {
            AddressEntity address = restaurant.getAddress();
            address.setStreet(dto.getAddress().getStreet());
            address.setNumber(dto.getAddress().getNumber());
            address.setCity(dto.getAddress().getCity());
            address.setPostalCode(dto.getAddress().getPostalCode());
        }

        // registra última alteração
        restaurant.setLastUpdate(LocalDateTime.now());

        RestaurantEntity saved = restaurantRepository.save(restaurant);
        return toResponse(saved);
    }


    //LOGIN
    @Override
    @Transactional
    public RestaurantResponseDTO login(String login, String password) {
        Optional<RestaurantEntity> optional =
                restaurantRepository.findByLoginAndPassword(login, password);
        if (optional.isEmpty()) {
            throw new InvalidLoginException("Invalid login or password");
        }
        RestaurantEntity restaurant = optional.get();
        return toResponse(restaurant);
  }


@Override
@Transactional
public List<RestaurantResponseDTO> searchByName(String name) {
    List<RestaurantEntity> restaurants =
            restaurantRepository.findByNameContainingIgnoreCase(name);
    List<RestaurantResponseDTO> responseList = new ArrayList<>();
    for (RestaurantEntity restaurant : restaurants) {
        responseList.add(toResponse(restaurant));
    }
    return responseList;
}


    @Override
    @Transactional
    public List<RestaurantDetailResponseDTO> findAll() {
        //  Busca todos os clientes no banco
        List<RestaurantEntity> restaurants = restaurantRepository.findAll();
        //  Cria a lista de resposta
        List<RestaurantDetailResponseDTO> responseList = new ArrayList<>();
        //  Converte Entity -> DetailResponseDTO
        for (RestaurantEntity restaurant : restaurants) {
            responseList.add(toDetailResponse(restaurant));
        }
        //  Retorna a lista detalhada
        return responseList;
    }



    @Override
    @Transactional
    public void updatePassword(Long id, PasswordRequestDTO dto) {
        Optional<RestaurantEntity> optional =
                restaurantRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ResourceNotFoundException("Restaurant not found");
        }
        RestaurantEntity restaurant = optional.get();
        // valida senha atual
        if (!restaurant.getPassword().equals(dto.getCurrentPassword())) {
            throw new InvalidPasswordException();
        }
        // atualiza senha e data
        restaurant.setPassword(dto.getPassword());
        restaurant.setLastUpdate(LocalDateTime.now());
        restaurantRepository.save(restaurant);
    }


    @Override
    @Transactional
    public void delete(long id) {
       if (!restaurantRepository.existsById(id)) {
          throw new ResourceNotFoundException("Restaurant not found");
       }
        restaurantRepository.deleteById(id);
    }


    // metodo de mapeamento de ENTITY PARA DTO
    private RestaurantResponseDTO toResponse(RestaurantEntity restaurant) {
        RestaurantResponseDTO dto = new RestaurantResponseDTO();
        dto.setId(restaurant.getId());
        dto.setName(restaurant.getName());
        dto.setEmail(restaurant.getEmail());
        dto.setLogin(restaurant.getLogin());
        dto.setLastUpdate(restaurant.getLastUpdate());
        return dto;
    }


    private RestaurantDetailResponseDTO toDetailResponse(RestaurantEntity restaurant) {
        RestaurantDetailResponseDTO dto = new RestaurantDetailResponseDTO();
        dto.setId(restaurant.getId());
        dto.setName(restaurant.getName());
        dto.setCnpj(restaurant.getCnpj());
        dto.setEmail(restaurant.getEmail());
        dto.setLogin(restaurant.getLogin());
        dto.setLastUpdate(restaurant.getLastUpdate());
        // se existe risco de cliente sem endereco
     if (restaurant.getAddress() != null) {
        AddressResponseDTO addressDTO = new AddressResponseDTO();
        addressDTO.setStreet(restaurant.getAddress().getStreet());
        addressDTO.setNumber(restaurant.getAddress().getNumber());
        addressDTO.setCity(restaurant.getAddress().getCity());
        addressDTO.setPostalCode(restaurant.getAddress().getPostalCode());
        dto.setAddress(addressDTO);
     }
        return dto;
    }
}