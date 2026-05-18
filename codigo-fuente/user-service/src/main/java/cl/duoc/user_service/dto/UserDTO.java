package cl.duoc.user_service.dto;


import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private Long idAuth;
    private String nombreCompleto;
    private String rut;
    private String fechaNacimiento;
    private String numeroTelefono;
    private String direccion;
    // No se incluyen datos innecesarios
}
