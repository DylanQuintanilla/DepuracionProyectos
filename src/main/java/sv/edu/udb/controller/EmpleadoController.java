package sv.edu.udb.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sv.edu.udb.controller.request.EmpleadoRequest;
import sv.edu.udb.controller.response.EmpleadoResponse;
import sv.edu.udb.service.EmpleadoService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "empleados")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    @GetMapping
    public List<EmpleadoResponse> listarEmpleados() {
        return empleadoService.findAll();
    }

    @GetMapping(path = "{id}")
    public EmpleadoResponse buscarEmpleadoPorId(@PathVariable(name = "id") final Long id) {
        return empleadoService.findById(id);
    }

    @PostMapping
    public EmpleadoResponse guardarEmpleado(@Valid @RequestBody final EmpleadoRequest empleadoRequest) {
        return empleadoService.save(empleadoRequest);
    }

    @PutMapping(path = "{id}")
    public EmpleadoResponse actualizarEmpleado(@PathVariable(name = "id") final Long id,
                                               @Valid @RequestBody final EmpleadoRequest empleadoRequest) {
        return empleadoService.update(id, empleadoRequest);
    }

    @DeleteMapping(path = "{id}")
    public void eliminarEmpleado(@PathVariable(name = "id") final Long id) {
        empleadoService.delete(id);
    }

}
