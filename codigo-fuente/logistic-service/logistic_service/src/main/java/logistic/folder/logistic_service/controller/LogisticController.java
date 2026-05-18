package logistic.folder.logistic_service.controller;

import logistic.folder.logistic_service.service.LogisticService;
import logistic.folder.logistic_service.dto.LogisticDTO;
import logistic.folder.logistic_service.dto.LogisticCreateDTO;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/envios")
public class LogisticController {
    private final LogisticService logisticService;

    public LogisticController(LogisticService logisticService) {
        this.logisticService= logisticService;
    }


    // Listar todos los Envios
    @GetMapping
   public ResponseEntity<List<LogisticDTO>> obtenerTodas() {
        return ResponseEntity.ok(logisticService.listarTodas());
    }


    // Buscar Envio por Id
    @GetMapping("/{id}")
    public ResponseEntity<LogisticDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(logisticService.findDtoById(id));
    }

    // Buscar Envio por Id de Orden
    @GetMapping("/idorden/{orderId}")
    public ResponseEntity<List<LogisticDTO>> buscarPorIdOrden(@PathVariable Long orderId) {
        List<LogisticDTO> envios = logisticService.obtenerPorOrderId(orderId);
        return envios.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(envios);
    }

    // Registrar un nuevo Envio
    @PostMapping
    public ResponseEntity<LogisticDTO> crearPaciente(@Valid @RequestBody LogisticCreateDTO dto) {
        LogisticDTO creado = logisticService.crearEnvio(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }



    // Eliminar Envio por Id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEnvio(@PathVariable Long id) {
        boolean eliminado = logisticService.eliminarEnvio(id);
        return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }


    // Actualizar Usuario por Id
    @PutMapping("/{id}")
    public ResponseEntity<LogisticDTO> actualizar(@PathVariable Long id,@Valid @RequestBody LogisticCreateDTO dto) {
        return ResponseEntity.ok(logisticService.actualizar(id, dto));
    }
}
