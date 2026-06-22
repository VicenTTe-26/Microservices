package comms.folder.comms_service.controller;

import comms.folder.comms_service.service.CommsService;
import comms.folder.comms_service.dto.CommsDTO;
import comms.folder.comms_service.dto.CommsCreateDTO;
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

@Tag(name = "Comms", description = "Gestion de notificaciones")
@RestController
@RequestMapping("api/v1/comms")
public class CommsController {
    private final CommsService commsService;

    public CommsController(CommsService commsService) {
        this.commsService= commsService;
    }


    // Listar todos los Mensajes
    @Operation(summary = "Listar todos los mensajes",
               description = "Retorna la lista completa de mensajes registrados en el sistema.")
    @ApiResponse(responseCode = "200", description = "Lista obtenida de forma correcta")
    @GetMapping
   public ResponseEntity<List<CommsDTO>> obtenerTodas() {
        return ResponseEntity.ok(commsService.listarTodas());
    }


    // Buscar Mensaje por Id
        @Operation(summary = "Buscar mensaje por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Mensaje encontrado"),
        @ApiResponse(responseCode = "404", description = "Mensaje no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CommsDTO> buscarPorId(@Parameter(description = "ID único del comms", required = true) @PathVariable Long id) {
        return ResponseEntity.ok(commsService.findDtoById(id));
    }

    // Buscar Mensaje por Id de Ordem
    @Operation(summary = "Buscar Mensaje por ID de Orden")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Orden encontrada"),
        @ApiResponse(responseCode = "404", description = "No hay mensaje para esa Orden")
    })
    @GetMapping("/idorden/{orderId}")
    public ResponseEntity<List<CommsDTO>> buscarPorIdOrden(@Parameter(description = "ID del usuario que está asociado al mensaje", required = true) @PathVariable Long orderId) {
        List<CommsDTO> mensajes = commsService.obtenerPorOrderId(orderId);
        return mensajes.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(mensajes);
    }

      // Buscar Mensaje por Id de User
    @Operation(summary = "Buscar Mensaje por ID de User")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User encontrado"),
        @ApiResponse(responseCode = "404", description = "No hay mensaje para ese User")
    })
    @GetMapping("/iduser/{userId}")
    public ResponseEntity<List<CommsDTO>> buscarPorIdUser(@Parameter(description = "ID del User que está asociado al mensaje", required = true) @PathVariable Long userId) {
        List<CommsDTO> mensajes = commsService.obtenerPorUserId(userId);
        return mensajes.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(mensajes);
    }

    // Registrar un nuevo Mensaje
    @Operation(summary = "Registrar un nuevo mensaje")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Mensaje creado"),
        @ApiResponse(responseCode = "400", description = "Datos incorrectos"),
        @ApiResponse(responseCode = "503", description = "Servicio Orden o User no disponible para solicitar el id")
    })
    @PostMapping
    public ResponseEntity<CommsDTO> crearMensaje(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos necesarios para la creacion", required = true) @Valid @RequestBody CommsCreateDTO dto) {
        CommsDTO creado = commsService.crearComms(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }



    // Eliminar Mensaje por Id
    @Operation(summary = "Eliminar mensaje por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Mensaje eliminado"),
        @ApiResponse(responseCode = "404", description = "Mensaje no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMensaje(@Parameter(description = "ID del mensaje a eliminar", required = true) @PathVariable Long id) {
        boolean eliminado = commsService.eliminarMensaje(id);
        return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }


    // Actualizar Mensaje por Id
    @Operation(summary = "Actualizar mensaje existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Actualización exitosa"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Mensaje no encontrado"),
        @ApiResponse(responseCode = "503", description = "Servicio Orden o User no disponible para solicitar el id")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CommsDTO> actualizarMensaje(@Parameter(description = "ID del mensaje a actualizar", required = true) @PathVariable Long id, @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Nuevos datos del mensaje", required = true) @Valid @RequestBody CommsCreateDTO dto) {
        return ResponseEntity.ok(commsService.actualizarMensaje(id, dto));
    }
}