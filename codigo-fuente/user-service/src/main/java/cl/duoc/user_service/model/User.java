package cl.duoc.user_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombreCompleto;

    @NotBlank(message = "El rut no puede estar vacío")
    private String rut;

    @NotBlank(message = "La fecha de nacimiento no puede estar vacia")
    private String fechaNacimiento;

    @NotBlank(message = "El número de telefono no puede estar vacio")
    private String numeroTelefono;

    @NotBlank(message = "El correo no puede estar vacio")
    private String correo;

    @NotBlank(message = "La direccion no puede estar vacia")
    private String direccion;
}
