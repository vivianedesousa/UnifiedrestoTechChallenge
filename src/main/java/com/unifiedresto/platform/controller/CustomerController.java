package com.unifiedresto.platform.controller;
import com.unifiedresto.platform.dto.*;
import com.unifiedresto.platform.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
// mnada git swagger
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Customers", description = "Endpoints relacionados aos clientes")
@RestController
@RequestMapping ("/api/v1/customers")
public class CustomerController {
  private final CustomerService customerService;
  public CustomerController(CustomerService   customerService) {
      this.customerService = customerService;
  }

    @Operation(summary = "Cadastrar cliente")
    @PostMapping
    public ResponseEntity<CustomerResponseDTO> create(
            @RequestBody CustomerRequestDTO dto) {
        CustomerResponseDTO response = customerService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

     @Operation(summary = "Atualizar dados do cliente pelo id ")
    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> update(
            @PathVariable Long id,
            @RequestBody CustomerRequestDTO dto) {
        CustomerResponseDTO response = customerService.update(id, dto);
        return ResponseEntity.ok(response);
    }

      @Operation(summary = "Login do cliente validando login e senha")
    @PostMapping("/login")
    public ResponseEntity<CustomerResponseDTO> login(
            @RequestBody LoginRequestDTO dto) {
        CustomerResponseDTO response =
                customerService.login(dto.getLogin(), dto.getPassword());
        return ResponseEntity.ok(response);
    }


    @Operation(summary = "Buscar clientes por nome")
    @GetMapping("/search")
    public ResponseEntity<List<CustomerResponseDTO>> searchByName(
            @RequestParam String name) { // receber o nome da url

        return ResponseEntity.ok(customerService.searchByName(name));
    }


    @Operation(summary = "Listar todos os clientes")
    @GetMapping
    public ResponseEntity<List<CustomerDetailResponseDTO>> findAll() {
        return ResponseEntity.ok(customerService.findAll());
    }


    @Operation(summary = "Alterar senha do cliente pelo id com enpoint separado")
    @PatchMapping("/{id}/password")
    public ResponseEntity<Void> updatePassword(
            @PathVariable Long id,
            @RequestBody PasswordRequestDTO dto) {

        customerService.updatePassword(id, dto);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Remover cliente pelo id ")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
