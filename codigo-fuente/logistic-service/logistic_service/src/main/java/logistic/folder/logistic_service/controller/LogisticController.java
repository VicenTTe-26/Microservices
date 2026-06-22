package logistic.folder.logistic_service.controller;

import logistic.folder.logistic_service.service.LogisticService;
import logistic.folder.logistic_service.dto.LogisticDTO;
import logistic.folder.logistic_service.dto.LogisticCreateDTO;
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

@Tag(name = "Envios", description = "Gestion de envios")
@RestController
@RequestMapping("api/v1/envios")
public class LogisticController {
    private final LogisticService logisticService;

    public LogisticController(LogisticService logisticService) {
        this.logisticService= logisticService;
    }


    // Listar todos los Envios
        @Operation(summary = "Listar todos los envios",
               description = "Retorna la lista completa de envios registrados en el sistema.")
    @ApiResponse(responseCode = "200", description = "Lista obtenida de forma correcta")
    @GetMapping
   public ResponseEntity<List<LogisticDTO>> obtenerTodas() {
        return ResponseEntity.ok(logisticService.listarTodas());
    }


    // Buscar Envio por Id
    @Operation(summary = "Buscar envio por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Envio encontrado"),
        @ApiResponse(responseCode = "404", description = "Envio no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<LogisticDTO> buscarPorId(@Parameter(description = "ID único del envio", required = true) @PathVariable Long id) {
        return ResponseEntity.ok(logisticService.findDtoById(id));
    }

    // Buscar Envio por Id de Orden
    @Operation(summary = "Buscar envio por ID de Orden")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Orden encontrada"),
        @ApiResponse(responseCode = "404", description = "No hay envio para esa Orden")
    })
    @GetMapping("/idorden/{orderId}")
    public ResponseEntity<List<LogisticDTO>> buscarPorIdOrden(@Parameter(description = "ID de la orden que está asociado al envio", required = true) @PathVariable Long orderId) {
        List<LogisticDTO> envios = logisticService.obtenerPorOrderId(orderId);
        return envios.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(envios);
    }

    // Registrar un nuevo Envio
    @Operation(summary = "Registrar un nuevo envio")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Envio creado"),
        @ApiResponse(responseCode = "400", description = "Datos incorrectos"),
        @ApiResponse(responseCode = "503", description = "Servicio Orden no disponible para solicitar el id")
    })
    @PostMapping
    public ResponseEntity<LogisticDTO> crearPaciente(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos necesarios para la creacion", required = true) @Valid @RequestBody LogisticCreateDTO dto) {
        LogisticDTO creado = logisticService.crearEnvio(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }



    // Eliminar Envio por Id
    @Operation(summary = "Eliminar envio por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Envio eliminado"),
        @ApiResponse(responseCode = "404", description = "Envio no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEnvio(@Parameter(description = "ID del envio a eliminar", required = true) @PathVariable Long id) {
        boolean eliminado = logisticService.eliminarEnvio(id);
        return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }


    // Actualizar Envio por Id
    @Operation(summary = "Actualizar Envio existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Actualización exitosa"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Envio no encontrado"),
        @ApiResponse(responseCode = "503", description = "Servicio Orden no disponible para solicitar el id")
    })
    @PutMapping("/{id}")
    public ResponseEntity<LogisticDTO> actualizar(@Parameter(description = "ID del envio a actualizar", required = true) @PathVariable Long id, @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Nuevos datos del envio", required = true) @Valid @RequestBody LogisticCreateDTO dto) {
        return ResponseEntity.ok(logisticService.actualizar(id, dto));
    }
}
