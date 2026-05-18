package cl.duoc.user_service.controller;


import cl.duoc.user_service.service.UserService;
import cl.duoc.user_service.dto.UserDTO;
import cl.duoc.user_service.dto.UserCreateDTO;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService= userService;
    }


    // Listar todos los usuarios
    @GetMapping
   public ResponseEntity<List<UserDTO>> obtenerTodas() {
        return ResponseEntity.ok(userService.listarTodas());
    }


    // Buscar Usuario por Id
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findDtoById(id));
    }

    // Buscar Usuario por Id de Auth
    @GetMapping("/idauth/{idAuth}")
    public ResponseEntity<List<UserDTO>> buscarPorIdAuth(@PathVariable Long idAuth) {
        List<UserDTO> usuarios = userService.obtenerPorAuthId(idAuth);
        return usuarios.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(usuarios);
    }

    // Registrar un nuevo Usuario
    @PostMapping
    public ResponseEntity<UserDTO> crearPaciente(@Valid @RequestBody UserCreateDTO dto) {
        UserDTO creado = userService.crearUsuario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }



    // Eliminar Usuario por Id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        boolean eliminado = userService.eliminarUser(id);
        return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }


    // Actualizar Usuario por Id
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> actualizar(@PathVariable Long id,@Valid @RequestBody UserCreateDTO dto) {
        return ResponseEntity.ok(userService.actualizar(id, dto));
    }
}
