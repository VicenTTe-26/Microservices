package comms.folder.comms_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommsCreateDTO {

    @Schema(description = "ID del user asociado proveniente del microservicio de User", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long userId;

    @Schema(description = "ID de la orden asociada proveniente del microservicio de Order", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long orderId;

    @Schema(description = "Correo al cual mandar el mensaje", example = "sadasdsad@dsdsd.com", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El correo no puede estar vacio")
    private String correo;

    @Schema(description = "Mensaje de la notificacion", example = "Le informamos que su pedido va en camino.", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El mensaje no puede estar vacio")
    private String mensaje;
}

