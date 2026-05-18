package logistic.folder.logistic_service.repository;

import logistic.folder.logistic_service.model.Envio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface LogisticRepository extends JpaRepository<Envio, Long> {
    List<Envio> findByOrderId(Long orderId);

}