package sv.edu.udb.service;

import sv.edu.udb.controller.request.EmpleadoRequest;
import sv.edu.udb.controller.response.EmpleadoResponse;

import java.util.List;

public interface EmpleadoService {

    List<EmpleadoResponse> findAll();

    EmpleadoResponse findById(final Long id);

    EmpleadoResponse save(final EmpleadoRequest empleadoRequest);

    EmpleadoResponse update(final Long id, final EmpleadoRequest empleadoRequest);

    void delete(final Long id);
}
