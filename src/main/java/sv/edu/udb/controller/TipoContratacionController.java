package sv.edu.udb.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sv.edu.udb.controller.request.TipoContratacionRequest;
import sv.edu.udb.controller.response.TipoContratacionResponse;
import sv.edu.udb.service.TipoContratacionService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "tipoContrataciones")
public class TipoContratacionController {

    private final TipoContratacionService tipoContratacionService;

    @GetMapping
    public List<TipoContratacionResponse> getTiposContratacion() {
        return tipoContratacionService.findAll();
    }

    @GetMapping(path = "{id}")
    public TipoContratacionResponse getTipoContratacion(@PathVariable(name = "id") Long id) {
        return tipoContratacionService.findById(id);
    }

    @PostMapping
    public TipoContratacionResponse addTipoContratacion(@Valid @RequestBody TipoContratacionRequest tipoContratacionRequest) {
        return tipoContratacionService.save(tipoContratacionRequest);
    }

    @PutMapping(path = "{id}")
    public TipoContratacionResponse updateTipoContratacion(@Valid @RequestBody TipoContratacionRequest tipoContratacionRequest,
                                                           @PathVariable(name = "id") Long id){
        return tipoContratacionService.update(id, tipoContratacionRequest);
    }

    @DeleteMapping(path = "{id}")
    public void deleteTipoContratacion(@PathVariable(name = "id") Long id) {
        tipoContratacionService.delete(id);
    }

}
