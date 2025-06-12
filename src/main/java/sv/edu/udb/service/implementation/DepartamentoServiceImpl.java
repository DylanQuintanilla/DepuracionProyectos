package sv.edu.udb.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sv.edu.udb.controller.request.DepartamentoRequest;
import sv.edu.udb.controller.response.DepartamentoResponse;
import sv.edu.udb.model.entity.Departamento;
import sv.edu.udb.repository.DepartamentoRepository;
import sv.edu.udb.service.DepartamentoService;
import sv.edu.udb.service.mapper.DepartamentoMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartamentoServiceImpl implements DepartamentoService {

    @NonNull
    private DepartamentoRepository departamentoRepository;

    @NonNull
    private DepartamentoMapper departamentoMapper;

    @Override
    public List<DepartamentoResponse> findAll() {
        return departamentoMapper.toDepartamentoResponseList(departamentoRepository.findAll());
    }

    @Override
    public DepartamentoResponse findById(Long id) {
        return departamentoMapper.toDepartamentoResponse(departamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Departamento no encontrado con el id: " + id)));
    }

    @Override
    public DepartamentoResponse save(DepartamentoRequest departamentoRequest) {
        return departamentoMapper.toDepartamentoResponse(departamentoRepository.save(departamentoMapper.toDepartamento(departamentoRequest)));
    }

    @Override
    public DepartamentoResponse update(Long id, DepartamentoRequest departamentoRequest) {
        Departamento departamentoActualizar = departamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Departamento no encontrado con el id: " + id));
        departamentoActualizar.setNombre(departamentoRequest.getNombre());
        departamentoActualizar.setDescripcion(departamentoRequest.getDescripcion());
        return departamentoMapper.toDepartamentoResponse(departamentoRepository.save(departamentoActualizar));
    }

    @Override
    public void delete(Long id) {
        departamentoRepository.deleteById(id);
    }
}
