package sv.edu.udb.service;

import sv.edu.udb.controller.request.ContratacionRequest;
import sv.edu.udb.controller.response.ContratacionResponse;
import sv.edu.udb.model.entity.Contratacion;

import java.util.List;

public interface ContratacionService {
    List<ContratacionResponse> findAll();

    ContratacionResponse findById(final Long id);

    ContratacionResponse save(final ContratacionRequest contratacionRequest);

    ContratacionResponse update(final ContratacionRequest contratacionRequest, final Long id);

    void delete(final Long id);
}
