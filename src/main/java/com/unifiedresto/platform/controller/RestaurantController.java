package com.unifiedresto.platform.controller;
import com.unifiedresto.platform.dto.*;
import com.unifiedresto.platform.service.RestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurants")
public class RestaurantController {
    private final RestaurantService restaurantService;
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }


    // pegar todos os resaturante detalhado
    @GetMapping
    public ResponseEntity<List<RestaurantDetailResponseDTO>> findAll() {
        return ResponseEntity.ok(restaurantService.findAll());
    }

    /* =========================
       SEARCH BY NAME (SIMPLES)
       GET /customers/search?name=ana
       ========================= */
    @GetMapping("/search")
    public ResponseEntity<List<RestaurantResponseDTO>> searchByName(
            @RequestParam String name) { // receber o nome da url
        return ResponseEntity.ok(restaurantService.searchByName(name));
    }

    /* =========================
       CREATE
       POST /restaurants
       ========================= */
    @PostMapping
    public ResponseEntity<RestaurantResponseDTO> create(
            @RequestBody RestaurantRequestDTO dto) {
        RestaurantResponseDTO response = restaurantService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /* =========================
       LOGIN /    // fazer login do db
       POST /restaurants/login
       “Validação de login obrigatória, por meio de um serviço que
        verifique se login e senha são válidos”
       ========================= */
    @PostMapping("/login")
    public ResponseEntity<RestaurantResponseDTO> login(
       @RequestBody LoginRequestDTO dto) {
        RestaurantResponseDTO response =
         restaurantService.login(dto.getLogin(), dto.getPassword());
        return ResponseEntity.ok(response);
    }



    /* =========================
       UPDATE PASSWORD
       PATCH /restaurants/{id}/password
       Troca de senha do usuário em endpoint separado” dentro DB
       ========================= */
    @PatchMapping("/{id}/password")
    public ResponseEntity<Void> updatePassword(
            @PathVariable Long id,
            @RequestBody PasswordRequestDTO dto) {
        restaurantService.updatePassword(id, dto);
        return ResponseEntity.noContent().build();
    }
    /* =========================
       UPDATE (SEM SENHA)
       PUT /restaurants/{id}
       //Optional<RestaurantEntity> findById(Long id);
       “Atualização das demais informações do usuário
       // //PUT/restaurants/{id}  Atualizacao do usuario sem (SENHA)
       em endpoint distinto do endpoint de senha”
       ========================= */
    @PutMapping("/{id}") // ate  aqui
    public ResponseEntity<RestaurantResponseDTO> update(
            @PathVariable Long id,
            @RequestBody RestaurantRequestDTO dto) {
        RestaurantResponseDTO response = restaurantService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    /* =========================
       DELETE
       DELETE /restaurants/{id}
       ========================= */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        restaurantService.delete(id);
        return ResponseEntity.noContent().build();
    }
}



