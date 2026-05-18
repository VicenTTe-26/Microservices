package auth.folder.auth_service.repository;

import auth.folder.auth_service.model.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface AuthRepository extends JpaRepository<Auth, Long> {

}