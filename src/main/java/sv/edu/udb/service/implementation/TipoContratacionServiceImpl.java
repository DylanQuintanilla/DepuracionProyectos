package sv.edu.udb.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sv.edu.udb.controller.request.TipoContratacionRequest;
import sv.edu.udb.controller.response.TipoContratacionResponse;
import sv.edu.udb.model.entity.TipoContratacion;
import sv.edu.udb.repository.TipoContratacionRepository;
import sv.edu.udb.service.TipoContratacionService;
import sv.edu.udb.service.mapper.TipoContratacionMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoContratacionServiceImpl implements TipoContratacionService {

    @NonNull
    private final TipoContratacionRepository tipoContratacionRepository;

    @NonNull
    private final TipoContratacionMapper tipoContratacionMapper;

    @Override
    public List<TipoContratacionResponse> findAll() {
        return tipoContratacionMapper.toTipoContratacionResponses(tipoContratacionRepository.findAll());
    }

    @Override
    public TipoContratacionResponse findById(Long id) {
        return tipoContratacionMapper.toTipoContratacionResponse(tipoContratacionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tipo de contratacion no encontrado con el id: " + id)));
    }

    @Override
    public TipoContratacionResponse save(TipoContratacionRequest tipoContratacionRequest) {
        return tipoContratacionMapper.toTipoContratacionResponse(tipoContratacionRepository.save(tipoContratacionMapper.toTipoContratacion(tipoContratacionRequest)));
    }

    @Override
    public TipoContratacionResponse update(Long id, TipoContratacionRequest tipoContratacionRequest) {
        TipoContratacion tipoContratacionActualizar = tipoContratacionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tipo de contratacion no encontrado con el id: " + id));
        tipoContratacionActualizar.setNombre(tipoContratacionRequest.getNombre());
        return tipoContratacionMapper.toTipoContratacionResponse(tipoContratacionRepository.save(tipoContratacionActualizar));
    }

    @Override
    public void delete(Long id) {
        tipoContratacionRepository.deleteById(id);
    }
}
