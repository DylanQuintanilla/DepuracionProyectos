package sv.edu.udb.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sv.edu.udb.controller.request.ContratacionRequest;
import sv.edu.udb.controller.response.ContratacionResponse;
import sv.edu.udb.model.entity.Contratacion;
import sv.edu.udb.repository.*;
import sv.edu.udb.service.ContratacionService;
import sv.edu.udb.service.mapper.ContratacionMapper;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContratacionServiceImpl implements ContratacionService {

    //Llamamos todos los repositorios porque los vamos a utilizar mas adelante
    @NonNull
    private final ContratacionRepository contratacionRepository;

    @NonNull
    private final EmpleadoRepository empleadoRepository;

    @NonNull
    private final CargoRepository cargoRepository;

    @NonNull
    private final DepartamentoRepository departamentoRepository;

    @NonNull
    private final TipoContratacionRepository tipoContratacionRepository;

    @NonNull
    private final ContratacionMapper contratacionMapper;


    @Override
    public List<ContratacionResponse> findAll() {
        return contratacionMapper.toContratacionResponseList(contratacionRepository.findAll());
    }

    @Override
    public ContratacionResponse findById(Long id) {
        return contratacionMapper.toContratacionResponse(
                contratacionRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Contratación no encontrada con ID: " + id))
        );
    }

    @Override
    public ContratacionResponse save(ContratacionRequest contratacionRequest) {
        Contratacion contratacionGuardar = contratacionMapper.toContratacion(contratacionRequest);
        setRelations(contratacionGuardar, contratacionRequest);
        contratacionGuardar.setFechaContratacion(LocalDate.now());
        return contratacionMapper.toContratacionResponse(contratacionRepository.save(contratacionGuardar));
    }

    @Override
    public ContratacionResponse update(ContratacionRequest contratacionRequest, Long id) {
        Contratacion contratacionActualizar = contratacionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Contratación no encontrada con ID: " + id));
        setRelations(contratacionActualizar, contratacionRequest);
        contratacionActualizar.setSalario(contratacionRequest.getSalario());
        contratacionActualizar.setEstado(contratacionRequest.getEstado());
        return contratacionMapper.toContratacionResponse(contratacionRepository.save(contratacionActualizar));
    }

    @Override
    public void delete(Long id) {
        contratacionRepository.deleteById(id);
    }

    private void setRelations(Contratacion contratacion, ContratacionRequest request) {
        contratacion.setEmpleado(empleadoRepository.findById(request.getEmpleadoId())
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado")));
        contratacion.setCargo(cargoRepository.findById(request.getCargoId())
                .orElseThrow(() -> new EntityNotFoundException("Cargo no encontrado")));
        contratacion.setDepartamento(departamentoRepository.findById(request.getDepartamentoId())
                .orElseThrow(() -> new EntityNotFoundException("Departamento no encontrado")));
        contratacion.setTipoContratacion(tipoContratacionRepository.findById(request.getTipoContratacionId())
                .orElseThrow(() -> new EntityNotFoundException("Tipo de contratación no encontrado")));
    }

}
