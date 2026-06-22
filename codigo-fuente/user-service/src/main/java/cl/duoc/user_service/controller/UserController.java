package cl.duoc.user_service.controller;


import cl.duoc.user_service.service.UserService;
import cl.duoc.user_service.dto.UserDTO;
import cl.duoc.user_service.dto.UserCreateDTO;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Imports de Swagger
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Usuarios", description = "Gestion de usuarios")
@RestController
@RequestMapping("api/v2/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService= userService;
    }


    // Listar todos los usuarios
    @Operation(summary = "Listar todos los usuarios",
               description = "Retorna la lista completa de usuarios registrados en el sistema.")
    @ApiResponse(responseCode = "200", description = "Lista obtenida de forma correcta")
    @GetMapping
   public ResponseEntity<List<UserDTO>> obtenerTodas() {
        return ResponseEntity.ok(userService.listarTodas());
    }


    // Buscar Usuario por Id
    @Operation(summary = "Buscar usuario por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> buscarPorId(@Parameter(description = "ID único del usuario", required = true) @PathVariable Long id) {
        return ResponseEntity.ok(userService.findDtoById(id));
    }

    // Buscar Usuario por Id de Auth
    @Operation(summary = "Buscar usuario por ID de Auth")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Auth encontrado"),
        @ApiResponse(responseCode = "404", description = "No hay usuario para ese Auth")
    })
    @GetMapping("/idauth/{idAuth}")
    public ResponseEntity<List<UserDTO>> buscarPorIdAuth(@Parameter(description = "ID del auth que está asociado al usuario", required = true) @PathVariable Long idAuth) {
        List<UserDTO> usuarios = userService.obtenerPorAuthId(idAuth);
        return usuarios.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(usuarios);
    }

    // Registrar un nuevo Usuario
    @Operation(summary = "Registrar un nuevo usuario")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Usuario creado"),
        @ApiResponse(responseCode = "400", description = "Datos incorrectos"),
        @ApiResponse(responseCode = "503", description = "Servicio Auth no disponible para solicitar el id")
    })
    @PostMapping
    public ResponseEntity<UserDTO> crearPaciente(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos necesarios para la creacion", required = true) @Valid @RequestBody UserCreateDTO dto) {
        UserDTO creado = userService.crearUsuario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }



    // Eliminar Usuario por Id
    @Operation(summary = "Eliminar usuario por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Usuario eliminado"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@Parameter(description = "ID del usuario a eliminar", required = true) @PathVariable Long id) {
        boolean eliminado = userService.eliminarUser(id);
        return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }


    // Actualizar Usuario por Id
    @Operation(summary = "Actualizar usuario existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Actualización exitosa"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
        @ApiResponse(responseCode = "503", description = "Servicio Auth no disponible para solicitar el id")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> actualizar(@Parameter(description = "ID del usuario a actualizar", required = true) @PathVariable Long id, @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Nuevos datos del usuario", required = true) @Valid @RequestBody UserCreateDTO dto) {
        return ResponseEntity.ok(userService.actualizar(id, dto));
    }
}
