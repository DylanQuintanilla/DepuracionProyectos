package sv.edu.udb.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sv.edu.udb.controller.request.EmpleadoRequest;
import sv.edu.udb.controller.response.EmpleadoResponse;
import sv.edu.udb.model.entity.Empleado;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmpleadoMapper {

    @Mapping(source = "dui", target = "dui")//Esta amotacion me ayudo porque me salia error que el dui estaba falso cuando no, pero aqui como que obligamos que apunte a la columna correcta
    Empleado toEmpleado(final EmpleadoRequest empleadoRequest);

    EmpleadoResponse toEmpleadoResponse(final Empleado empleado);

    List<EmpleadoResponse> toEmpleadoResponseList(final List<Empleado> empleados);

}
