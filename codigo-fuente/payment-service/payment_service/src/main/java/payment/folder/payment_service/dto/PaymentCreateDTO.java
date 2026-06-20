package payment.folder.payment_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentCreateDTO {

    @Schema(description = "ID de la orden asociada proveniente del microservicio de Order", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long orderId;

    @Schema(description = "Monto total del pago", example = "10000", requiredMode = Schema.RequiredMode.REQUIRED)
    private Double totalAmount;

    @Schema(description = "metodo de pago aplicado", example = "efectivo", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El metodo es obligatorio")
    private String metodoPago;

}