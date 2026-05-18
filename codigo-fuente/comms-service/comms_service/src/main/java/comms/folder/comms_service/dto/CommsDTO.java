package comms.folder.comms_service.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommsDTO {
    private Long id;
    private Long userId;
    private Long orderId;
    private String correo;
    private String mensaje;
    // No se incluyen datos innecesarios
}
