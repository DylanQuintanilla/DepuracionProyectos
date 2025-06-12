package sv.edu.udb.service.mapper;

import jakarta.validation.constraints.Pattern;
import org.mapstruct.Mapper;
import sv.edu.udb.controller.request.DepartamentoRequest;
import sv.edu.udb.controller.response.DepartamentoResponse;
import sv.edu.udb.model.entity.Departamento;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DepartamentoMapper {

    Departamento toDepartamento(final DepartamentoRequest departamentoRequest);

    DepartamentoResponse toDepartamentoResponse(final Departamento departamento);

    List<DepartamentoResponse> toDepartamentoResponseList(final List<Departamento> departamentos);
}
