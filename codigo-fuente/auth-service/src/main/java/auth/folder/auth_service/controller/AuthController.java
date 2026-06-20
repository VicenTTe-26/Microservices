package auth.folder.auth_service.controller;

import auth.folder.auth_service.service.AuthService;
import auth.folder.auth_service.dto.AuthCreateDTO;
import auth.folder.auth_service.dto.AuthDTO;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Auth", description = "Gestion de autenticaciones")
@RestController
@RequestMapping("api/v2/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService= authService;
    }

    // Registrar una nueva Auth
    @Operation(summary = "Registrar una nueva Auth")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Auth creado"),
        @ApiResponse(responseCode = "400", description = "Datos incorrectos")
    })
    @PostMapping
    public ResponseEntity<AuthDTO> crearAuth(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos necesarios para la creacion", required = true) @Valid @RequestBody AuthCreateDTO dto) {
        AuthDTO nuevoAuth = authService.crearAuthUsuario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoAuth);
    }

    // Listar todas las Auth
    @Operation(summary = "Listar todos los auth",
               description = "Retorna la lista completa de auth registradas en el sistema.")
    @ApiResponse(responseCode = "200", description = "Lista obtenida de forma correcta")
    @GetMapping
    public ResponseEntity<List<AuthDTO>> listarAuth() {
        List<AuthDTO> auth = authService.listarAuth();
        return ResponseEntity.ok(auth);
    }

    // Buscar Auth por Id
        @Operation(summary = "Buscar auth por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Auth encontrado"),
        @ApiResponse(responseCode = "404", description = "Auth no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AuthDTO> buscarAuthPorId(@Parameter(description = "ID único del auth", required = true) @PathVariable Long id) {
        return ResponseEntity.ok(authService.buscarAuthPorId(id));

    }

    // Eliminar Auth por Id
    @Operation(summary = "Eliminar auth por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Auth eliminado"),
        @ApiResponse(responseCode = "404", description = "Auth no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@Parameter(description = "ID del auth a eliminar", required = true) @PathVariable Long id) {
        boolean eliminado = authService.eliminarAuth(id);
        if (eliminado) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Actualizar Usuario por Id
    @Operation(summary = "Actualizar auth existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Actualización exitosa"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Auth no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<AuthDTO> actualizarAuth(@Parameter(description = "ID del auth a actualizar", required = true) @PathVariable Long id, @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Nuevos datos del auth", required = true) @Valid @RequestBody AuthCreateDTO authActualizado) {
        AuthDTO auth = authService.actualizarAuth(id, authActualizado);
        if (auth != null) {
            return ResponseEntity.ok(auth);
        } else {
            return ResponseEntity.notFound().build();  
        }
    }
}