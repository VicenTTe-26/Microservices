package cl.duoc.user_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDTO {


    private Long idAuth;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombreCompleto;

    @NotBlank(message = "El RUT es obligatorio")
    private String rut;

    @NotBlank(message = "La fecha de nacimiento no puede estar vacia")
    private String fechaNacimiento;

    @NotBlank(message = "El número de telefono es obligatorio")
    private String numeroTelefono;

    @NotBlank(message = "La direccion no puede estar vacia")
    private String direccion;

}