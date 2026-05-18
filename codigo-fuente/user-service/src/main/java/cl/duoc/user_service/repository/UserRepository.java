package cl.duoc.user_service.repository;


import cl.duoc.user_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;



@Repository
public interface UserRepository extends JpaRepository<User, Long> {
        List<User> findByIdAuth(Long idAuth);
}