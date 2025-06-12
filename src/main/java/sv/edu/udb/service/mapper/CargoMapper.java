package sv.edu.udb.service.mapper;

import org.mapstruct.Mapper;
import sv.edu.udb.controller.request.CargoRequest;
import sv.edu.udb.controller.response.CargoResponse;
import sv.edu.udb.model.entity.Cargo;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CargoMapper {

    Cargo toCargo(final CargoRequest cargoRequest);

    CargoResponse toCargoResponse(final Cargo cargo);

    List<CargoResponse> toCargoResponseList(final List<Cargo> cargos);

}
