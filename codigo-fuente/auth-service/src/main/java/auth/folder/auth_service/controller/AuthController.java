package auth.folder.auth_service.controller;

import auth.folder.auth_service.model.Auth;
import auth.folder.auth_service.service.AuthService;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService= authService;
    }

    // Registrar una nueva Auth
    @PostMapping
    public ResponseEntity<Auth> registrarAuth(@Valid @RequestBody Auth auth) {
        Auth nuevoAuth = authService.AuthRegister(auth);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoAuth);
    }

    // Listar todas las Auth
    @GetMapping
    public ResponseEntity<List<Auth>> listarAuth() {
        List<Auth> auth = authService.listarAuth();
        return ResponseEntity.ok(auth);
    }

    // Buscar Auth por Id
    @GetMapping("/{id}")
    public ResponseEntity<Auth> buscarPorId(@PathVariable Long id) {
        Optional<Auth> auth = authService.buscarPorId(id);
        if(auth.isPresent()) {
            return ResponseEntity.ok(auth.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // Buscar Auth por Nombre
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<Auth>> buscarPorNombre(@PathVariable String nombre) {
        List<Auth> auth = authService.buscarPorNombre(nombre);

        if (auth.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(auth);
    }


    // Buscar Auth por Correo
   @GetMapping("/correo/{correo}")
    public ResponseEntity<List<Auth>> buscarPorCorreo(@PathVariable String correo) {
        List<Auth> auth = authService.buscarPorCorreo(correo);

        if (auth.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(auth);
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
    public ResponseEntity<Auth> actualizarAuth(@PathVariable Long id, @Valid @RequestBody Auth authActualizado) {
        Auth auth = authService.actualizarAuth(id, authActualizado);
        if (auth != null) {
            return ResponseEntity.ok(auth);
        } else {
            return ResponseEntity.notFound().build();  
        }
    }
}