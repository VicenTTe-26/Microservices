package logistic.folder.logistic_service.model;

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
public class Envio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;

    @NotBlank(message = "El nombre no puede estar vacio")
    private String destinatarioNombre;

    @NotBlank(message = "La direccion no puede estar vacia")
    private String direccionCompleta;

    @NotBlank(message = "El proveedor no puede estar vacio")
    private String proveedorEnvio;

    @NotBlank(message = "El estado no puede estar vacio")
    private String estadoEnvio;

    @NotBlank(message = "La fecha no puede estar vacia")
    private String fechaEstimadaEntrega;

}
