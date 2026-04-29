package cl.duoc.user_service.repository;


import cl.duoc.user_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByNombreCompleto(String nombreCompleto);
    List<User> findByRut(String rut);
    List<User> findByNumeroTelefono(String numeroTelefono);
    List<User> findByCorreo(String correo);

}