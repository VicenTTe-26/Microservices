package auth.folder.auth_service.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthDTO {
    private Long id;
    private String nombre;
    private String correo;
    // No se incluyen datos innecesarios
}