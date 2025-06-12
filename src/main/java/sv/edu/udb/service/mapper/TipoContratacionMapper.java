package sv.edu.udb.service.mapper;

import org.mapstruct.Mapper;
import sv.edu.udb.controller.request.TipoContratacionRequest;
import sv.edu.udb.controller.response.TipoContratacionResponse;
import sv.edu.udb.model.entity.TipoContratacion;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TipoContratacionMapper {

    TipoContratacion toTipoContratacion(TipoContratacionRequest tipoContratacionRequest);

    TipoContratacionResponse toTipoContratacionResponse(TipoContratacion tipoContratacion);

    List<TipoContratacionResponse> toTipoContratacionResponses(List<TipoContratacion> tipoContrataciones);
}
