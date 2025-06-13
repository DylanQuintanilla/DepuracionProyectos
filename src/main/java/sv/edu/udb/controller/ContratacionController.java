package sv.edu.udb.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sv.edu.udb.controller.request.ContratacionRequest;
import sv.edu.udb.controller.response.ContratacionResponse;
import sv.edu.udb.service.ContratacionService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "contrataciones")
public class ContratacionController {

    private final ContratacionService contratacionService;

    @GetMapping
    public List<ContratacionResponse> getContratacion() {
        return contratacionService.findAll();
    }

    @GetMapping(path = "{id}")
    public ContratacionResponse getContratacionById(@PathVariable(name = "id")final Long id) {
        return contratacionService.findById(id);
    }

    @PostMapping
    public ContratacionResponse createContratacion(@Valid @RequestBody ContratacionRequest contratacionRequest) {
        return contratacionService.save(contratacionRequest);
    }

    @PutMapping(path = "{id}")
    public ContratacionResponse actualizarCintratacion(@PathVariable(name = "id")final Long id, @Valid @RequestBody ContratacionRequest contratacionRequest) {
        return contratacionService.update(contratacionRequest, id);
    }

    @DeleteMapping(path = "{id}")
    public void eliminarCintratacion(@PathVariable(name = "id")final Long id) {
        contratacionService.delete(id);
    }
}
