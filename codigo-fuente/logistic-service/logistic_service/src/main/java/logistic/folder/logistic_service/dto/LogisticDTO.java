package logistic.folder.logistic_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogisticDTO {
    private Long id;
    private Long orderId;
    private String destinatarioNombre;
    private String direccionCompleta;
    private String proveedorEnvio;
    private String estadoEnvio;
    private String fechaEstimadaEntrega;
}
