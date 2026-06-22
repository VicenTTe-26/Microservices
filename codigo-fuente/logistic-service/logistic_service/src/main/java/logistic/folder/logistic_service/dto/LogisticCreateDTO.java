package logistic.folder.logistic_service.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogisticCreateDTO {

    @Schema(description = "ID dela orden asociada proveniente del microservicio de Order", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long orderId;

    @Schema(description = "Nombre del destinatario del envío", example = "Juan Carlos", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El nombre no puede estar vacio")
    private String destinatarioNombre;

    @Schema(description = "Direccion a la cual va a ser enviado el pedido", example = "Psje Roma 123", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "La direccion no puede estar vacia")
    private String direccionCompleta;

    @Schema(description = "Empresa que provee el pedido", example = "Bluexpress", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El proveedor no puede estar vacio")
    private String proveedorEnvio;

    @Schema(description = "Estado del envio", example = "En preparacion", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El estado no puede estar vacio")
    private String estadoEnvio;

    @Schema(description = "Fecha estimada de la entrega", example = " miercoles 24 de enero", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "La fecha no puede estar vacia")
    private String fechaEstimadaEntrega;

}
