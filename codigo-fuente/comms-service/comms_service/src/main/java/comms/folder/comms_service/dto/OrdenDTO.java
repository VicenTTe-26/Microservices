package comms.folder.comms_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdenDTO {
    @Schema(description = "ID unico de la orden", example = "1")
    private Long id;

    @Schema(description = "ID del usuario asociado", example = "3")
    private Long idUsuario;

    @Schema(description = "Fecha y hora de creacion de la orden", example = "30/01/2026T15:30:00")
    private LocalDateTime fechaCreacion;

    @Schema(description = "Total de la orden", example = "50000")
    private Double total;

    @Schema(description = "Estado de la orden", example = "pagado")
    private String estado;
}