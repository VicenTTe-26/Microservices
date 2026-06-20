package auth.folder.auth_service.dto;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthDTO {
    @Schema(description = "Identificador único del auth", example = "1")
    private Long id;

    @Schema(description = "Nombre del auth", example = "Vicente Araya")
    private String nombre;

    @Schema(description = "Correo del auth", example = "asdsadsa@asdasd.cl")
    private String correo;
    // No se incluyen datos innecesarios
}