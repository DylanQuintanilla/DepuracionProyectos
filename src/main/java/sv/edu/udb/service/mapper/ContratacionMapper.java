package sv.edu.udb.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sv.edu.udb.controller.request.ContratacionRequest;
import sv.edu.udb.controller.response.ContratacionResponse;
import sv.edu.udb.model.entity.Contratacion;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContratacionMapper {

    @Mapping(source = "empleado.nombre", target = "empleadoNombre")
    @Mapping(source = "cargo.cargo", target = "cargoNombre")
    @Mapping(source = "departamento.nombre", target = "departamentoNombre")
    @Mapping(source = "tipoContratacion.nombre", target = "tipoContratacionNombre")
    ContratacionResponse toContratacionResponse(Contratacion contratacion);

    List<ContratacionResponse> toContratacionResponseList(List<Contratacion> contrataciones);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "estado", constant = "true")
    @Mapping(target = "empleado", source = "empleadoId")
    @Mapping(target = "cargo", source = "cargoId")
    @Mapping(target = "departamento", source = "departamentoId")
    @Mapping(target = "tipoContratacion", source = "tipoContratacionId")
    Contratacion toContratacion(ContratacionRequest contratacionRequest);
}
