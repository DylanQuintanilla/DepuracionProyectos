package sv.edu.udb.service;

import sv.edu.udb.controller.request.CargoRequest;
import sv.edu.udb.controller.response.CargoResponse;
import sv.edu.udb.model.entity.Cargo;

import java.util.List;

public interface CargoService {

    List<CargoResponse> findAll();

    CargoResponse findById(final Long id);

    CargoResponse save(final CargoRequest cargoRequest);

    CargoResponse update(final Long id, final CargoRequest cargoRequest);

    void deleteById(final Long id);

}
