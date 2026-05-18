package auth.folder.auth_service.controller;

import auth.folder.auth_service.service.AuthService;
import auth.folder.auth_service.dto.AuthCreateDTO;
import auth.folder.auth_service.dto.AuthDTO;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService= authService;
    }

    // Registrar una nueva Auth
    @PostMapping
    public ResponseEntity<AuthDTO> crearAuth(@Valid @RequestBody AuthCreateDTO dto) {
        AuthDTO nuevoAuth = authService.crearAuthUsuario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoAuth);
    }

    // Listar todas las Auth
    @GetMapping
    public ResponseEntity<List<AuthDTO>> listarAuth() {
        List<AuthDTO> auth = authService.listarAuth();
        return ResponseEntity.ok(auth);
    }

    // Buscar Auth por Id
    @GetMapping("/{id}")
    public ResponseEntity<AuthDTO> buscarAuthPorId(@PathVariable Long id) {
        return ResponseEntity.ok(authService.buscarAuthPorId(id));

    }

    // Eliminar Auth por Id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        boolean eliminado = authService.eliminarAuth(id);
        if (eliminado) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Actualizar Usuario por Id
    @PutMapping("/{id}")
    public ResponseEntity<AuthDTO> actualizarAuth(@PathVariable Long id, @Valid @RequestBody AuthCreateDTO authActualizado) {
        AuthDTO auth = authService.actualizarAuth(id, authActualizado);
        if (auth != null) {
            return ResponseEntity.ok(auth);
        } else {
            return ResponseEntity.notFound().build();  
        }
    }
}