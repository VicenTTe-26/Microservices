package payment.folder.payment_service.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
    private Long id;
    private Long orderId;
    private Double totalAmount;
    private String metodoPago;
    // No se incluyen datos innecesarios
}