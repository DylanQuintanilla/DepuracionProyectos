package sv.edu.udb.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sv.edu.udb.controller.request.EmpleadoRequest;
import sv.edu.udb.controller.response.EmpleadoResponse;
import sv.edu.udb.model.entity.Empleado;
import sv.edu.udb.repository.EmpleadoRepository;
import sv.edu.udb.service.EmpleadoService;
import sv.edu.udb.service.mapper.EmpleadoMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpleadoServiceImpl implements EmpleadoService {

    @NonNull
    private final EmpleadoRepository empleadoRepository;

    @NonNull
    private final EmpleadoMapper empleadoMapper;

    //-----------------------Logica-------------------------------

    @Override
    public List<EmpleadoResponse> findAll() {
        return empleadoMapper.toEmpleadoResponseList(empleadoRepository.findAll());
    }

    @Override
    public EmpleadoResponse findById(Long id) {
        return empleadoMapper.toEmpleadoResponse(empleadoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empelado no encontrado con el id: " + id)));
    }

    @Override
    public EmpleadoResponse save(EmpleadoRequest empleadoRequest) {

        /*final Empleado empleado = empleadoMapper.toEmpleado(empleadoRequest);
        return empleadoMapper.toEmpleadoResponse(empleadoRepository.save(empleado)); Esta una dforma separada pero se puede de esta otra*/

        return empleadoMapper.toEmpleadoResponse(empleadoRepository.save(empleadoMapper.toEmpleado(empleadoRequest)));
    }

    @Override
    public EmpleadoResponse update(Long id, EmpleadoRequest empleadoRequest) {
        final Empleado empleadoActualizar = empleadoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empelado no encontrado con el id: " + id));
        empleadoActualizar.setDui((empleadoRequest.getDui()));
        empleadoActualizar.setNombre(empleadoRequest.getNombre());
        empleadoActualizar.setNumeroTelefono(empleadoRequest.getNumeroTelefono());
        empleadoActualizar.setCorreo(empleadoRequest.getCorreo());
        empleadoActualizar.setFechaNacimiento(empleadoRequest.getFechaNacimiento());

        return empleadoMapper.toEmpleadoResponse(empleadoRepository.save(empleadoActualizar));
    }

    @Override
    public void delete(Long id) {
        empleadoRepository.deleteById(id);
    }
}
