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
// esta classe esta implementando o contrato interface Restaurante service com as regras
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
        //   validações simples de acordo com requistos
        if (restaurantRepository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyExistsException();
        }

        if (restaurantRepository.existsByLogin(dto.getLogin())) {
            throw new LoginAlreadyExistsException();
        }

        if (restaurantRepository.existsByCnpj(dto.getCnpj())) {
            throw new CnpjAlreadyExistsException();
        }
        // crian o endereco
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

    @Override
    @Transactional
    //  UPDATE (SEM SENHA)
    public RestaurantResponseDTO update(Long id, RestaurantRequestDTO dto) {
        Optional<RestaurantEntity> optionalRestaurant =
                restaurantRepository.findById(id);

        if (optionalRestaurant.isEmpty()) {
            throw new ResourceNotFoundException("Restaurant not found");
        }
        RestaurantEntity restaurant = optionalRestaurant.get();

        // atualiza dados básicos
        restaurant.setName(dto.getName());
        restaurant.setEmail(dto.getEmail());
        restaurant.setLogin(dto.getLogin());

        // atualiza endereço
        AddressEntity address = restaurant.getAddress();
        address.setStreet(dto.getAddress().getStreet());
        address.setNumber(dto.getAddress().getNumber());
        address.setCity(dto.getAddress().getCity());
        address.setPostalCode(dto.getAddress().getPostalCode());

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
        // busca os restaurantes no banco
        List<RestaurantEntity> restaurants =
                restaurantRepository.findByNameContainingIgnoreCase(name);

        // lista que será retornada
        List<RestaurantResponseDTO> responseList = new ArrayList<>();

        // percorre a lista de entidades
        for (RestaurantEntity restaurant : restaurants) {

            // converte Entity -> ResponseDTO
            RestaurantResponseDTO dto = new RestaurantResponseDTO();
            dto.setId(restaurant.getId());
            dto.setName(restaurant.getName());
            dto.setEmail(restaurant.getEmail());
            dto.setLogin(restaurant.getLogin());

            // adiciona na lista de resposta
            responseList.add(dto);
        }
        return responseList;
    }

    @Override
    public List<RestaurantDetailResponseDTO> findAll() {
        // 1. Busca todos os clientes no banco
        List<RestaurantEntity> restaurants = restaurantRepository.findAll();
        // 2. Cria a lista de resposta
        List<RestaurantDetailResponseDTO> responseList = new ArrayList<>();
        // 3. Converte Entity -> DetailResponseDTO
        for (RestaurantEntity restaurant : restaurants) {
            responseList.add(toDetailResponse(restaurant));
        }
        // 4. Retorna a lista detalhada
        return responseList;
    }

    // UPDATE PASSWORD somemente enpoint separado
    @Override
    @Transactional
    public void updatePassword(Long id, PasswordRequestDTO dto) {
        Optional<RestaurantEntity> optional = restaurantRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ResourceNotFoundException("Restaurant not found");
        }
        RestaurantEntity restaurant = optional.get();
        //  PRIMEIRO valida a senha atual
        if (!restaurant.getPassword().equals(dto.getCurrentPassword())) {
            throw new InvalidPasswordException();
        }
        //  DEPOIS troca a senha
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
//    @Override
//    public List<RestaurantEntity> findAll() {
//        return restaurantRepository.findAll();
//    }
//
//    @Override
//    public List<RestaurantEntity> searchByName(String name) {
//        return restaurantRepository.findByNameContainingIgnoreCase(name);
//    }
//
////    @Override
////    @Transactional
////    public RestaurantEntity create(RestaurantRequestDTO dto) {
////        //mapeamento DTO → Entity.
////        AddressEntity address = new AddressEntity();
////        address.setStreet(dto.getAddress().getStreet());
////        address.setNumber(dto.getAddress().getNumber());
////        address.setCity(dto.getAddress().getCity());
////        address.setPostalCode(dto.getAddress().getPostalCode());
////        AddressEntity savedAddress = addressRepository.save(address);
////
////        // Restaurant
////        RestaurantEntity restaurant = new RestaurantEntity();
////        restaurant.setName(dto.getName());
////        restaurant.setCnpj(dto.getCnpj());
////        restaurant.setEmail(dto.getEmail());
////        restaurant.setLogin(dto.getLogin());
////        restaurant.setPassword(dto.getPassword()); // hash depois
////        restaurant.setAddress(savedAddress);
////        return restaurantRepository.save(restaurant);
////    }
//
//    //Atualizacao do usuariof sem (SENHA)
//     @Transactional
//    public RestaurantEntity update(Long id, RestaurantRequestDTO dto) {
//        // busca o cliente
//        Optional<RestaurantEntity> optionalRestaurant = restaurantRepository.findById(id);
//        if (optionalRestaurant.isEmpty()) {
//            throw new RuntimeException("Restaurant was not found");
//        }
//        RestaurantEntity restaurant = optionalRestaurant.get();
//
//        // atualiza dados basicos
//        restaurant.setName(dto.getName());
//        restaurant.setEmail(dto.getEmail());
//        restaurant.setLogin(dto.getLogin());
//
//        // atualiza endereço
//        AddressEntity address = restaurant.getAddress();
//        address.setStreet(dto.getAddress().getStreet());
//        address.setNumber(dto.getAddress().getNumber());
//        address.setCity(dto.getAddress().getCity());
//        address.setPostalCode(dto.getAddress().getPostalCode());
//
//        // registro da última alteraçao
//        restaurant.setLastUpdate(LocalDateTime.now());
//
//        // salva e retorna
//        return restaurantRepository.save(restaurant);
//
//    }
//    @Override
//    @Transactional
//    public void delete(long id) {
//        // valida se o restaurante existe
//        if (!restaurantRepository.existsById(id)) {
//            throw new RuntimeException("Restaurant was not found");
//        }
//        // deleta o restaurante
//        restaurantRepository.deleteById(id);
//    }
