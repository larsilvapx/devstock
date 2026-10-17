package br.com.devstock.devstock.controller;

import br.com.devstock.devstock.entity.Desenvolvedor;
import br.com.devstock.devstock.service.DesenvolvedorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/desenvolvedores")
public class DesenvolvedorController {

    private final DesenvolvedorService desenvolvedorService;

    public DesenvolvedorController(DesenvolvedorService desenvolvedorService) {
        this.desenvolvedorService = desenvolvedorService;
    }

    @GetMapping
    public List<Desenvolvedor> listarTodos() {
        return desenvolvedorService.listarTodos();
    }

    @GetMapping("/{id}")
    public Desenvolvedor buscarPorId(@PathVariable Long id) {
        return desenvolvedorService.buscarPorId(id);
    }

    @PostMapping
    public Desenvolvedor cadastrar(
            @RequestBody
            Desenvolvedor desenvolvedor) {
        return desenvolvedorService.cadastrar(desenvolvedor);
    }

}
