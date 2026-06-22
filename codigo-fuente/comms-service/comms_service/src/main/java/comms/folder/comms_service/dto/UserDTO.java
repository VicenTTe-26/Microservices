package comms.folder.comms_service.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @Schema(description = "ID auto-incremental único del usuario", example = "1")
    private Long id;

    @Schema(description = "ID del auth asociado", example = "3")
    private Long idAuth;

    @Schema(description = "Nombre completo del usuario", example = "Vicente Araya Salazar")
    private String nombreCompleto;

    @Schema(description = "RUT identificador del usuario", example = "22.222.222-2")
    private String rut;

    @Schema(description = "Fecha de nacimiento del usuario", example = "05/10/2026")
    private String fechaNacimiento;

    @Schema(description = "Número de telefono del usuario", example = "111111111")
    private String numeroTelefono;

    @Schema(description = "Direccion completa del usuario", example = "Psje Juan Perez 234")
    private String direccion;
    // No se incluyen datos innecesarios
}