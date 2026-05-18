package comms.folder.comms_service.controller;

import comms.folder.comms_service.service.CommsService;
import comms.folder.comms_service.dto.CommsDTO;
import comms.folder.comms_service.dto.CommsCreateDTO;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/comms")
public class CommsController {
    private final CommsService commsService;

    public CommsController(CommsService commsService) {
        this.commsService= commsService;
    }


    // Listar todos los Mnesajes
    @GetMapping
   public ResponseEntity<List<CommsDTO>> obtenerTodas() {
        return ResponseEntity.ok(commsService.listarTodas());
    }


    // Buscar Mensaje por Id
    @GetMapping("/{id}")
    public ResponseEntity<CommsDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(commsService.findDtoById(id));
    }

    // Buscar Usuario por Id de User
    @GetMapping("/iduser/{userId}")
    public ResponseEntity<List<CommsDTO>> buscarPorIdOrden(@PathVariable Long orderId) {
        List<CommsDTO> mensajes = commsService.obtenerPorUserId(orderId);
        return mensajes.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(mensajes);
    }

      // Buscar Usuario por Id de Orden
    @GetMapping("/idorden/{orderId}")
    public ResponseEntity<List<CommsDTO>> buscarPorIdUser(@PathVariable Long userId) {
        List<CommsDTO> mensajes = commsService.obtenerPorUserId(userId);
        return mensajes.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(mensajes);
    }

    // Registrar un nuevo Mensaje
    @PostMapping
    public ResponseEntity<CommsDTO> crearMensaje(@Valid @RequestBody CommsCreateDTO dto) {
        CommsDTO creado = commsService.crearComms(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }



    // Eliminar Usuario por Id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMensaje(@PathVariable Long id) {
        boolean eliminado = commsService.eliminarMensaje(id);
        return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }


    // Actualizar Mensaje por Id
    @PutMapping("/{id}")
    public ResponseEntity<CommsDTO> actualizarMensaje(@PathVariable Long id,@Valid @RequestBody CommsCreateDTO dto) {
        return ResponseEntity.ok(commsService.actualizarMensaje(id, dto));
    }
}