package com.unifiedresto.platform.controller;
import com.unifiedresto.platform.dto.*;
import com.unifiedresto.platform.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping ("/api/v1/customers")
public class CustomerController {
  private final CustomerService customerService;
  public CustomerController(CustomerService   customerService) {
      this.customerService = customerService;
  }
    /* =========================
       CREATE CUSTOMER
       POST /customers
       ========================= */

    @PostMapping
    public ResponseEntity<CustomerResponseDTO> create(
            @RequestBody CustomerRequestDTO dto) {
        CustomerResponseDTO response = customerService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    /* =========================
       UPDATE (SEM SENHA)
       PUT /customers/{id}
       ========================= */
    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> update(
            @PathVariable Long id,
            @RequestBody CustomerRequestDTO dto) {
        CustomerResponseDTO response = customerService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    /* =========================
       LOGIN
       POST /customers/login // concera rodape
       ========================= */
    @PostMapping("/login")
    public ResponseEntity<CustomerResponseDTO> login(
            @RequestBody LoginRequestDTO dto) {

        CustomerResponseDTO response =
                customerService.login(dto.getLogin(), dto.getPassword());
        return ResponseEntity.ok(response);
    }

    /* =========================
     FIND ALL (DETALHADO)
     GET /customers
     ========================= */

    /* =========================
       SEARCH BY NAME (SIMPLES)
       GET /customers/search?name=ana
       ========================= */
    @GetMapping("/search")
    public ResponseEntity<List<CustomerResponseDTO>> searchByName(
            @RequestParam String name) { // receber o nome da url

        return ResponseEntity.ok(customerService.searchByName(name));
    }


    @GetMapping
    public ResponseEntity<List<CustomerDetailResponseDTO>> findAll() {
        return ResponseEntity.ok(customerService.findAll());
    }

    /* =========================
       UPDATE PASSWORD
       PATCH /customers/{id}/password
       ========================= */
    @PatchMapping("/{id}/password")
    public ResponseEntity<Void> updatePassword(
            @PathVariable Long id,
            @RequestBody PasswordRequestDTO dto) {

        customerService.updatePassword(id, dto);
        return ResponseEntity.noContent().build();
    }

    /* =========================
       DELETE
       DELETE /customers/{id}
       ========================= */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
