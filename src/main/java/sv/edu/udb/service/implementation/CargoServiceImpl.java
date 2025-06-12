package sv.edu.udb.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sv.edu.udb.controller.request.CargoRequest;
import sv.edu.udb.controller.response.CargoResponse;
import sv.edu.udb.model.entity.Cargo;
import sv.edu.udb.repository.CargoRepository;
import sv.edu.udb.service.CargoService;
import sv.edu.udb.service.mapper.CargoMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CargoServiceImpl implements CargoService {

    @NonNull
    private final CargoRepository cargoRepository;

    @NonNull
    private final CargoMapper cargoMapper;

    @Override
    public List<CargoResponse> findAll() {
        return cargoMapper.toCargoResponseList(cargoRepository.findAll());
    }

    @Override
    public CargoResponse findById(Long id) {
        return cargoMapper.toCargoResponse(cargoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cargo no encontrado con el id: " + id)));
    }

    @Override
    public CargoResponse save(CargoRequest cargoRequest) {
        return cargoMapper.toCargoResponse(cargoRepository.save(cargoMapper.toCargo(cargoRequest)));
    }

    @Override
    public CargoResponse update(Long id, CargoRequest cargoRequest) {
        Cargo cargoActualizar = cargoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cargo no encontrado con el id: " + id));
        cargoActualizar.setCargo(cargoRequest.getCargo());
        cargoActualizar.setDescripcion(cargoRequest.getDescripcion());
        cargoActualizar.setJefatura(cargoRequest.getJefatura());
        return cargoMapper.toCargoResponse(cargoRepository.save(cargoActualizar));
    }

    @Override
    public void deleteById(Long id) {
        cargoRepository.deleteById(id);
    }
}
