package payment.folder.payment_service.dto;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
    @Schema(description = "ID auto-incremental único del pago", example = "1")
    private Long id;

    @Schema(description = "ID de la orden asociada", example = "3")
    private Long orderId;

    @Schema(description = "Monto total del pago", example = "30000")
    private Double totalAmount;

    @Schema(description = "metodo de pago efectuado", example = "tarjeta")
    private String metodoPago;
    // No se incluyen datos innecesarios
}