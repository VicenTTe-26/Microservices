package comms.folder.comms_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommsCreateDTO {

    private Long userId;

    private Long orderId;

    @NotBlank(message = "El correo no puede estar vacio")
    private String correo;

    @NotBlank(message = "El mensaje no puede estar vacio")
    private String mensaje;
}

