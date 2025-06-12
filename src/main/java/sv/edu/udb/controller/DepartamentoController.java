package sv.edu.udb.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sv.edu.udb.controller.request.DepartamentoRequest;
import sv.edu.udb.controller.response.DepartamentoResponse;
import sv.edu.udb.service.DepartamentoService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "departamentos")
public class DepartamentoController {

    //@Autowired porque no necesito esto que raro
    private final DepartamentoService departamentoService;

    @GetMapping
    public List<DepartamentoResponse> getAll() {
        return departamentoService.findAll();
    }

    @GetMapping(path = "{id}")
    public DepartamentoResponse getById(@PathVariable(name = "id")final Long id) {
        return departamentoService.findById(id);
    }

    @PostMapping
    public DepartamentoResponse create(@Valid @RequestBody final DepartamentoRequest departamentoRequest) {
        return departamentoService.save(departamentoRequest);
    }

    @PutMapping(path = "{id}")
    public DepartamentoResponse update(@Valid @RequestBody final DepartamentoRequest departamentoRequest, @PathVariable(name = "id")final Long id) {
        return departamentoService.update(id, departamentoRequest);
    }

    @DeleteMapping(path = "{id}")
    public void delete(@PathVariable(name = "id")final Long id) {
        departamentoService.delete(id);
    }
}
