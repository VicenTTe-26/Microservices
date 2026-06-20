package auth.folder.auth_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthCreateDTO {

    @Schema(description = "Nombre del auth nuevo", example = "Vicente Araya Salazar", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @Schema(description = "Email del auth", example = "asdsadasd@dadas.cl", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El email está mal escrito")
    private String correo;

    @Schema(description = "Contraseña del auth", example = "pepito1234", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "La contraseña no puede estar vacia")
    @Size(min = 4, message = "Minimo 4 caracteres")
    private String contraseña;

}