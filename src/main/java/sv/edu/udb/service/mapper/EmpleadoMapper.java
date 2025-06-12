package sv.edu.udb.service.mapper;

import org.mapstruct.Mapper;
import sv.edu.udb.controller.request.EmpleadoRequest;
import sv.edu.udb.controller.response.EmpleadoResponse;
import sv.edu.udb.model.entity.Empleado;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmpleadoMapper {

    Empleado toEmpleado(final EmpleadoRequest empleadoRequest);

    EmpleadoResponse toEmpleadoResponse(final Empleado empleado);

    List<EmpleadoResponse> toEmpleadoResponseList(final List<Empleado> empleados);

}
