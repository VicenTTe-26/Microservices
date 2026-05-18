package payment.folder.payment_service.controller;

import payment.folder.payment_service.service.PaymentService;
import payment.folder.payment_service.dto.PaymentDTO;
import payment.folder.payment_service.dto.PaymentCreateDTO;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/pagos")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService= paymentService;
    }


    // Listar todos los pagos
    @GetMapping
   public ResponseEntity<List<PaymentDTO>> obtenerTodas() {
        return ResponseEntity.ok(paymentService.listarTodas());
    }


    // Buscar Pago por Id
    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.findDtoById(id));
    }

    // Buscar Pago por Id de Orden
    @GetMapping("/idauth/{idAuth}")
    public ResponseEntity<List<PaymentDTO>> buscarPorOrderId(@PathVariable Long orderId) {
        List<PaymentDTO> pagos = paymentService.obtenerPorOrdenId(orderId);
        return pagos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(pagos);
    }

    // Registrar un nuevo Pago
    @PostMapping
    public ResponseEntity<PaymentDTO> crearPago(@Valid @RequestBody PaymentCreateDTO dto) {
        PaymentDTO creado = paymentService.crearPago(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }



    // Eliminar Pago por Id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        boolean eliminado = paymentService.eliminarPago(id);
        return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }


    // Actualizar Usuario por Id
    @PutMapping("/{id}")
    public ResponseEntity<PaymentDTO> actualizar(@PathVariable Long id,@Valid @RequestBody PaymentCreateDTO dto) {
        return ResponseEntity.ok(paymentService.actualizar(id, dto));
    }
}
