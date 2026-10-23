
package br.com.devstock.devstock.service;

import br.com.devstock.devstock.entity.Notebook;
import br.com.devstock.devstock.exception.ConflitoCadastroException;
import br.com.devstock.devstock.repository.NotebookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotebookService {

    private final NotebookRepository notebookRepository;

    public NotebookService(NotebookRepository notebookRepository) {
        this.notebookRepository = notebookRepository;
    }
    public Notebook cadastrar(Notebook notebook){
        if(notebookRepository.existsBySerialNumber(notebook.getSerialNumber())){
            throw new ConflitoCadastroException(
                    "Já existe um notebook com este número de série."
            );
        }
        return notebookRepository.save(notebook);
    }

    public List<Notebook> listarTodos() {
        return notebookRepository.findAll();
    }

    public Notebook buscarPorId(Long id) {
        return notebookRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Notebook não encontrado: " + id));
    }
}
