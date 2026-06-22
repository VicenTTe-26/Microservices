package logistic.folder.logistic_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogisticDTO {
    @Schema(description = "ID auto incrementable del envio", example = "1")
    private Long id;

    @Schema(description = "ID dela orden asociada proveniente del microservicio de Order", example = "1")
    private Long orderId;

    @Schema(description = "Nombre del destinatario del envío", example = "Juan Carlos")
    private String destinatarioNombre;

    @Schema(description = "Direccion a la cual va a ser enviado el pedido", example = "Psje Roma 123")
    private String direccionCompleta;

    @Schema(description = "Empresa que provee el pedido", example = "Bluexpress")
    private String proveedorEnvio;

    @Schema(description = "Estado del envio", example = "En preparacion")
    private String estadoEnvio;

    @Schema(description = "Fecha estimada de la entrega", example = " miercoles 24 de enero")
    private String fechaEstimadaEntrega;
}
