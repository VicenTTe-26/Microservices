package payment.folder.payment_service.controller;

import payment.folder.payment_service.service.PaymentService;
import payment.folder.payment_service.dto.PaymentDTO;
import payment.folder.payment_service.dto.PaymentCreateDTO;
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

@Tag(name = "Pagos", description = "Gestion de pagos")
@RestController
@RequestMapping("api/v1/pagos")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService= paymentService;
    }


    // Listar todos los pagos
    @Operation(summary = "Listar todos los pagos",
               description = "Retorna la lista completa de pagos registrados en el sistema.")
    @ApiResponse(responseCode = "200", description = "Lista obtenida de forma correcta")
    @GetMapping
   public ResponseEntity<List<PaymentDTO>> obtenerTodas() {
        return ResponseEntity.ok(paymentService.listarTodas());
    }


    // Buscar Pago por Id
    @Operation(summary = "Buscar pago por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pago encontrado"),
        @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> buscarPorId(@Parameter(description = "ID único del pago", required = true) @PathVariable Long id) {
        return ResponseEntity.ok(paymentService.findDtoById(id));
    }

    // Buscar Pago por Id de Orden
        @Operation(summary = "Buscar Pago por ID de Orden")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Orden encontrado"),
        @ApiResponse(responseCode = "404", description = "No hay pago para esa Orden")
    })
    @GetMapping("/orderid/{orderId}")
    public ResponseEntity<List<PaymentDTO>> buscarPorOrderId(@Parameter(description = "ID de la orden que está asociado al pago", required = true) @PathVariable Long orderId) {
        List<PaymentDTO> pagos = paymentService.obtenerPorOrdenId(orderId);
        return pagos.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(pagos);
    }

    // Registrar un nuevo Pago
    @Operation(summary = "Registrar un nuevo pago")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Pago creado"),
        @ApiResponse(responseCode = "400", description = "Datos incorrectos"),
        @ApiResponse(responseCode = "503", description = "Servicio Order no disponible para solicitar el id")
    })
    @PostMapping
    public ResponseEntity<PaymentDTO> crearPago(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos necesarios para la creacion", required = true) @Valid @RequestBody PaymentCreateDTO dto) {
        PaymentDTO creado = paymentService.crearPago(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }



    // Eliminar Pago por Id
    @Operation(summary = "Eliminar pago por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Pago eliminado"),
        @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPago(@Parameter(description = "ID del pago a eliminar", required = true) @PathVariable Long id) {
        boolean eliminado = paymentService.eliminarPago(id);
        return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }


    // Actualizar Pago por Id
    @Operation(summary = "Actualizar pago existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Actualización exitosa"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PaymentDTO> actualizar(@Parameter(description = "ID del pago a actualizar", required = true) @PathVariable Long id, @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Nuevos datos del pago", required = true) @Valid @RequestBody PaymentCreateDTO dto) {
        return ResponseEntity.ok(paymentService.actualizar(id, dto));
    }
}
