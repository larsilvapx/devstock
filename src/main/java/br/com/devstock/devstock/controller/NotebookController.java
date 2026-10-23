
    package br.com.devstock.devstock.controller;

    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestBody;

    import br.com.devstock.devstock.entity.Notebook;
    import br.com.devstock.devstock.service.NotebookService;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;

    @RestController
    @RequestMapping("/api/notebooks")
    public class NotebookController {

        private final NotebookService notebookService;

        public NotebookController(NotebookService notebookService) {
            this.notebookService = notebookService;
        }

        @PostMapping
        public Notebook cadastrar(@RequestBody Notebook notebook){
            return notebookService.cadastrar(notebook);
        }

        @GetMapping
        public List<Notebook> listarTodos() {
            return notebookService.listarTodos();
        }

        @GetMapping("/{id}")
        public Notebook buscarPorId(@PathVariable Long id) {
            return notebookService.buscarPorId(id);
        }
    }
