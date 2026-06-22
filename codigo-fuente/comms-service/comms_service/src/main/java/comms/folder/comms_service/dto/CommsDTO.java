package comms.folder.comms_service.dto;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommsDTO {
    @Schema(description = "ID unico de las comms", example = "1")
    private Long id;

    @Schema(description = "ID del usuario asociado a la comm", example = "1")
    private Long userId;

    @Schema(description = "ID de la orden asociada a la comm", example = "1")
    private Long orderId;

    @Schema(description = "Correo al cual mandar el mensaje", example = "sadasdsad@dsdsd.com")
    private String correo;

    @Schema(description = "Mensaje de la notificacion", example = "Le informamos que su pedido va en camino.")
    private String mensaje;
    // No se incluyen datos innecesarios
}
