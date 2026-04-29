package auth.folder.auth_service.model;

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
public class Auth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @NotBlank(message = "El rut no puede estar vacío")
    @Email(message = "El email es incorrecto")
    private String correo;

    @NotBlank(message = "La contraseña no puede estar vacia")
    @Size(min = 4, message = "Minimo 4 caracteres")
    private String contraseña;
}