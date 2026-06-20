package cl.duoc.user_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Datos requeridos para la creacion o para actualizar un usuario")
public class UserCreateDTO {

    @Schema(description = "ID del auth asociado proveniente del microservicio de Auth", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long idAuth;

    @Schema(description = "Nombre completo del usuario", example = "Vicente Araya Salazar", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombreCompleto;

    @Schema(description = "RUT identificador del usuario", example = "22.222.222-2", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El RUT es obligatorio")
    private String rut;

    @Schema(description = "Fecha de nacimiento del usuario", example = "05/10/2026", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "La fecha de nacimiento no puede estar vacia")
    private String fechaNacimiento;

    @Schema(description = "Número de telefono del usuario", example = "111111111", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El número de telefono es obligatorio")
    private String numeroTelefono;

    @Schema(description = "Direccion completa del usuario", example = "Psje Juan Perez 234", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "La direccion no puede estar vacia")
    private String direccion;

}