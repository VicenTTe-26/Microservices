package comms.folder.comms_service.client;

import comms.folder.comms_service.dto.OrdenDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
    name = "Conexion-Ordenes",
    url = "${orden.service.url}"
)
public interface OrdenClient {

    @GetMapping("/api/v1/ordenes/{id}")
    OrdenDTO buscarOrdenPorId(@PathVariable("id") Long id);
}

