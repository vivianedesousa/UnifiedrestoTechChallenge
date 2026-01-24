package com.unifiedresto.platform.controller;
import com.unifiedresto.platform.dto.*;
import com.unifiedresto.platform.service.RestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
// mnada git swagger
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
@Tag(name = "Restaurants", description = "Endpoints relacionados aos restaurantes")
@RestController
@RequestMapping("/api/v1/restaurants")
public class RestaurantController {
    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @Operation(summary = "Cadastrar restaurante")
    @PostMapping
    public ResponseEntity<RestaurantResponseDTO> create(
            @RequestBody RestaurantRequestDTO dto) {
        RestaurantResponseDTO response = restaurantService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Listar todos os restaurantes")
    @GetMapping
    // pegar todos os resaturante detalhado
    public ResponseEntity<List<RestaurantDetailResponseDTO>> findAll() {
        return ResponseEntity.ok(restaurantService.findAll());
    }

    @Operation(summary = "Buscar restaurantes por nome")
    @GetMapping("/search")
    public ResponseEntity<List<RestaurantResponseDTO>> searchByName(
            @RequestParam String name) { // receber o nome da url
        return ResponseEntity.ok(restaurantService.searchByName(name));
    }

    @Operation(summary = "Login do restaurante validando login e senha")
    @PostMapping("/login")
    public ResponseEntity<RestaurantResponseDTO> login(
            @RequestBody LoginRequestDTO dto) {
        RestaurantResponseDTO response =
                restaurantService.login(dto.getLogin(), dto.getPassword());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Alterar senha do restaurante pelo id no enpoint separado")
    @PatchMapping("/{id}/password")

   public ResponseEntity<Void> updatePassword(
           @PathVariable Long id,
           @RequestBody PasswordRequestDTO dto) {
        restaurantService.updatePassword(id, dto);
       return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Atualizar dados do restaurante pelo id")
  @PutMapping("/{id}") // ate  aqui
    public ResponseEntity<RestaurantResponseDTO> update(
            @PathVariable Long id,
            @RequestBody RestaurantRequestDTO dto) {
        RestaurantResponseDTO response = restaurantService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Remover restaurante pelo id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        restaurantService.delete(id);
        return ResponseEntity.noContent().build();

    }
}



