
package br.com.devstock.devstock.controller;

import br.com.devstock.devstock.entity.Kit;
import br.com.devstock.devstock.service.KitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/kits")
public class KitController {

    private final KitService kitService;

    public KitController(KitService kitService) {
        this.kitService = kitService;
    }

    @GetMapping
    public ResponseEntity<List<Kit>> listarTodos() {
        return ResponseEntity.ok(kitService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Kit> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(kitService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Kit> cadastrar(@RequestBody Kit kit) {

        Kit kitCadastrado = kitService.cadastrar(kit);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(kitCadastrado.getId())
                .toUri();

        return ResponseEntity.created(location).body(kitCadastrado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Kit> atualizar(
            @PathVariable Long id,
            @RequestBody Kit kit
    ) {
        return ResponseEntity.ok(kitService.atualizar(id, kit));
    }
}

