package payment.folder.payment_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentCreateDTO {

    private Long orderId;

    private Double totalAmount;

    @NotBlank(message = "El metodo es obligatorio")
    private String metodoPago;

}