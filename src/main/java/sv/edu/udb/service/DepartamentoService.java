package sv.edu.udb.service;

import sv.edu.udb.controller.request.DepartamentoRequest;
import sv.edu.udb.controller.response.DepartamentoResponse;
import sv.edu.udb.model.entity.Departamento;

import java.util.List;

public interface DepartamentoService {
    List<DepartamentoResponse> findAll();

    DepartamentoResponse findById(final Long id);

    DepartamentoResponse save(final DepartamentoRequest departamentoRequest);

    DepartamentoResponse update(final Long id, final DepartamentoRequest departamentoRequest);

    void delete(final Long id);
}
