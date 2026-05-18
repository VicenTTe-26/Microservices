package comms.folder.comms_service.repository;

import comms.folder.comms_service.model.Comms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface CommsRepository extends JpaRepository<Comms, Long> {
    List<Comms> findByOrderId(Long orderId);
    List<Comms> findByUserId(Long userId);

}