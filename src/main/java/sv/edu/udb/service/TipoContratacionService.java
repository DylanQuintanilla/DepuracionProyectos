package sv.edu.udb.service;

import sv.edu.udb.controller.request.TipoContratacionRequest;
import sv.edu.udb.controller.response.TipoContratacionResponse;

import java.util.List;

public interface TipoContratacionService {

   List<TipoContratacionResponse> findAll();

   TipoContratacionResponse findById(final Long id);

   TipoContratacionResponse save(final TipoContratacionRequest tipoContratacionRequest);

   TipoContratacionResponse update(final Long id, final TipoContratacionRequest tipoContratacionRequest);

   void delete(final Long id);
}
