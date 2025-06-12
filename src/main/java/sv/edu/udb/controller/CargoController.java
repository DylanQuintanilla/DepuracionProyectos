package sv.edu.udb.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sv.edu.udb.controller.request.CargoRequest;
import sv.edu.udb.controller.response.CargoResponse;
import sv.edu.udb.service.CargoService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "cargos")
public class CargoController {

    private final CargoService cargoService;

    @GetMapping
    public List<CargoResponse> getAllCargos() {
        return cargoService.findAll();
    }

    @GetMapping(path = "{id}")
    public CargoResponse getCargoById(@PathVariable(name = "id") final Long id) {
        return cargoService.findById(id);
    }

    @PostMapping
    public CargoResponse guardarCargo(@Valid @RequestBody final CargoRequest cargoRequest) {
        return cargoService.save(cargoRequest);
    }

    @PutMapping(path = "{id}")
    public CargoResponse actualizarCargo(@Valid @RequestBody final CargoRequest cargoRequest,
                                         @PathVariable(name = "id") final Long id) {
        return cargoService.update(id, cargoRequest);
    }

    @DeleteMapping(path = "{id}")
    public void eliminarCargo(@PathVariable(name = "id") final Long id) {
        cargoService.deleteById(id);
    }
}
