package logistic.folder.logistic_service.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogisticCreateDTO {

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
