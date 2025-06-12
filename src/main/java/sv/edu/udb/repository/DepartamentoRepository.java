package sv.edu.udb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository; //Interesante este No trabaja con JPA pero puede hacer operaciones CRUD pero muy basicas y ademas que no permite la paginacion
import org.springframework.stereotype.Repository;
import sv.edu.udb.model.entity.Departamento;

@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {
}
