package sv.edu.udb.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sv.edu.udb.controller.request.ContratacionRequest;
import sv.edu.udb.controller.response.ContratacionResponse;
import sv.edu.udb.model.entity.Contratacion;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContratacionMapper {

    /*Practicamente aqui estamos mapeando parte por parte es decir como si lo hicieramos de forma manual
    * pero en este caso lo haremos con el @Mapping*/
    //los que tengan ignore = true, es porque o se asignan automaticamente (el caso del id) o lo asignaremos despues el caso de empleado, cargo, etc en el impl
    @Mapping(target = "id", ignore = true)
    //Aqui asignamos al estado como true
    @Mapping(target = "estado", constant = "true")
    //Le asiganmos la fecha de ahora
    @Mapping(target = "empleado", ignore = true)
    @Mapping(target = "cargo", ignore = true)
    @Mapping(target = "departamento", ignore = true)
    @Mapping(target = "tipoContratacion", ignore = true)
    //Aqui le damos el valor del request de salario al valor de la entidad salario
    @Mapping(target = "salario", source = "salario")
    Contratacion toContratacion(ContratacionRequest request);

    List<ContratacionResponse> toContratacionResponseList(List<Contratacion> contrataciones);

    //Toma el nombre del empleado desde la relación empleado y lo pone en empleadoNombre que se encuentra en el response
    @Mapping(source = "empleado.nombre", target = "empleadoNombre")
    @Mapping(source = "cargo.cargo", target = "cargoNombre")
    @Mapping(source = "departamento.nombre", target = "departamentoNombre")
    @Mapping(source = "tipoContratacion.nombre", target = "tipoContratacionNombre")
    ContratacionResponse toContratacionResponse(Contratacion contratacion);
}
