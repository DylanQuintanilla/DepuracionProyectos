package sv.edu.udb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sv.edu.udb.model.entity.TipoContratacion;

@Repository
public interface TipoContratacionRepository extends JpaRepository<TipoContratacion, Long> {
}
