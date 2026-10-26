
    package br.com.devstock.devstock.controller;

    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestBody;

    import br.com.devstock.devstock.entity.Notebook;
    import br.com.devstock.devstock.service.NotebookService;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

    import java.net.URI;
    import java.util.List;

    @RestController
    @RequestMapping("/api/notebooks")
    public class NotebookController {

        private final NotebookService notebookService;

        public NotebookController(NotebookService notebookService) {
            this.notebookService = notebookService;
        }

        @PostMapping
        public ResponseEntity<Notebook> cadastrar(
                @RequestBody Notebook notebook) {

            Notebook notebookSalvo = notebookService.cadastrar(notebook);

            URI localizacao = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(notebookSalvo.getId())
                    .toUri();

            return ResponseEntity
                    .created(localizacao)
                    .body(notebookSalvo);
        }

        @GetMapping
        public List<Notebook> listarTodos() {
            return notebookService.listarTodos();
        }

        @GetMapping("/{id}")
        public Notebook buscarPorId(@PathVariable Long id) {
            return notebookService.buscarPorId(id);
        }

        @PutMapping("/{id}")
        public Notebook atualizar(@PathVariable Long id,
                                  @RequestBody Notebook notebook){
            return notebookService.atualizar(id, notebook);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> excluir(@PathVariable Long id){
            notebookService.excluir(id);
            return ResponseEntity.noContent().build();
        }
    }
