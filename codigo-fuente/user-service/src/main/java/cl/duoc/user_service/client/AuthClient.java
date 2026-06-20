package cl.duoc.user_service.client;

import cl.duoc.user_service.dto.AuthDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
    name = "Auth-Service",
    url = "${auth.service.url}"
)
public interface AuthClient {

    @GetMapping("/api/v2/auth/{id}")
    AuthDTO buscarAuthPorId(@PathVariable("id") Long id);
}
