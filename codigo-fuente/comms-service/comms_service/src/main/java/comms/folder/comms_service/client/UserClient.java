package comms.folder.comms_service.client;

import comms.folder.comms_service.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
    name = "User-Service",
    url = "${user.service.url}"
)
public interface UserClient {

    @GetMapping("/api/v1/users/{id}")
    UserDTO buscarUserPorId(@PathVariable("id") Long id);
}