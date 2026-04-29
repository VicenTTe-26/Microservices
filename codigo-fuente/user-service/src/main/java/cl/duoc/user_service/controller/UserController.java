package cl.duoc.user_service.controller;


import cl.duoc.user_service.model.User;
import cl.duoc.user_service.service.UserService;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService= userService;
    }

    // Registrar un nuevo Usuario
    @PostMapping
    public ResponseEntity<User> registrarUsuario(@Valid @RequestBody User user) {
        User nuevoUsuario = userService.registrarUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
    }

    // Listar todos los usuarios
    @GetMapping
    public ResponseEntity<List<User>> listarUsuarios() {
        List<User> user = userService.listarUsuarios();
        return ResponseEntity.ok(user);
    }

    // Buscar Usuario por Id
    @GetMapping("/{id}")
    public ResponseEntity<User> buscarPorId(@PathVariable Long id) {
        Optional<User> user = userService.buscarPorId(id);
        if(user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Buscar libro por Nombre Completo
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<User>> buscarPorNombre(@PathVariable String nombreCompleto) {
        List<User> user = userService.buscarPorNombre(nombreCompleto);

        if (user.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(user);
    }


    // Buscar Usuario por Rut
   @GetMapping("/rut/{rut}")
    public ResponseEntity<List<User>> buscarPorRut(@PathVariable String rut) {
        List<User> user = userService.buscarPorRut(rut);

        if (user.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(user);
    }


    // Buscar Usuario por Teléfono
   @GetMapping("/telefono/{telefono}")
    public ResponseEntity<List<User>> buscarPorTelefono(@PathVariable String numeroTelefono) {
        List<User> user = userService.buscarPorTelefono(numeroTelefono);

        if (user.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(user);
    }

    // Buscar Usuario por Correo
   @GetMapping("/correo/{correo}")
    public ResponseEntity<List<User>> buscarPorCorreo(@PathVariable String correo) {
        List<User> user = userService.buscarPorCorreo(correo);

        if (user.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(user);
    }


    // Eliminar Usuario por Id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        boolean eliminado = userService.eliminarUsuario(id);
        if (eliminado) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Actualizar Usuario por Id
    @PutMapping("/{id}")
    public ResponseEntity<User> actualizarUsuario(@PathVariable Long id, @Valid @RequestBody User userActualizado) {
        User user = userService.actualizarUsuario(id, userActualizado);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();  
        }
    }
}
