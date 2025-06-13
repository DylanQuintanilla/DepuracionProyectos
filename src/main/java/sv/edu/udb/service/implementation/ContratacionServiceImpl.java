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
        Contratacion contratacion = contratacionMapper.toContratacion(contratacionRequest);
        setRelations(contratacion, contratacionRequest);

        // Asignar la fecha actual
        contratacion.setFechaContratacion(new Date()); // o LocalDate.now() si usas java.time
        contratacion.setEstado(true); // Si también quieres activarlo por defecto

        return contratacionMapper.toContratacionResponse(contratacionRepository.save(contratacion));
    }

    @Override
    public ContratacionResponse update(ContratacionRequest contratacionRequest, Long id) {
        Contratacion contratacion = contratacionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Contratación no encontrada con ID: " + id));

        setRelations(contratacion, contratacionRequest);

        // Actualizar campos específicos
        contratacion.setSalario(contratacionRequest.getSalario());
        contratacion.setEstado(contratacionRequest.getEstado());

        return contratacionMapper.toContratacionResponse(contratacionRepository.save(contratacion));
    }

    @Override
    public void delete(Long id) {
        contratacionRepository.deleteById(id);
    }

    private void setRelations(Contratacion contratacion, ContratacionRequest contratacionRequest) {
        // Obtener y asignar el Empleado
        contratacion.setEmpleado(empleadoRepository.findById(contratacionRequest.getEmpleadoId())
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado")));

        // Obtener y asignar el Cargo
        contratacion.setCargo(cargoRepository.findById(contratacionRequest.getCargoId())
                .orElseThrow(() -> new EntityNotFoundException("Cargo no encontrado")));

        // Obtener y asignar el Departamento
        contratacion.setDepartamento(departamentoRepository.findById(contratacionRequest.getDepartamentoId())
                .orElseThrow(() -> new EntityNotFoundException("Departamento no encontrado")));

        // Obtener y asignar el TipoContratacion
        contratacion.setTipoContratacion(tipoContratacionRepository.findById(contratacionRequest.getTipoContratacionId())
                .orElseThrow(() -> new EntityNotFoundException("Tipo de contratación no encontrado")));
    }

}
