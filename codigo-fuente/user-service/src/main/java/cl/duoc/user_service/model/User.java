package cl.duoc.user_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idAuth;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombreCompleto;

    @NotBlank(message = "El RUT es obligatorio")
    private String rut;

    @NotBlank(message = "La fecha de nacimiento no puede estar vacia")
    private String fechaNacimiento;

    @NotBlank(message = "El número de telefono no puede estar vacio")
    private String numeroTelefono;

    @NotBlank(message = "La direccion no puede estar vacia")
    private String direccion;
}
