package auth.folder.auth_service.repository;

import auth.folder.auth_service.model.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface AuthRepository extends JpaRepository<Auth, Long> {
    List<Auth> findByNombre(String nombre);
    List<Auth> findByCorreo(String correo);

}