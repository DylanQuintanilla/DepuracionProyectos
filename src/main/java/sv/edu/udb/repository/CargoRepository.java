package sv.edu.udb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sv.edu.udb.model.entity.Cargo;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Long> {

    // Verifica si existe un cargo cuyo campo "cargo" sea igual (ignorando mayúsculas)
    boolean existsByCargoIgnoreCase(String cargo);

}
