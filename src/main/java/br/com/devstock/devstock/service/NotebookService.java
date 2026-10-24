
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

    public Notebook cadastrar(Notebook notebook) {
        if (notebookRepository.existsBySerialNumber(notebook.getSerialNumber())) {
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

    public Notebook atualizar(Long id, Notebook notebookAtualizado) {

        // busca notebook existente no banco
        Notebook notebookExistente = buscarPorId(id);

        //verifica se o número de sério foi alterado
        // e se o novo novo número já pertence a outro notebook
        if (!java.util.Objects.equals(
                notebookExistente.getSerialNumber(),
                notebookAtualizado.getSerialNumber())
                && notebookRepository.existsBySerialNumber(
                notebookAtualizado.getSerialNumber())) {
            throw new ConflitoCadastroException(
                    "Já existe outro notebook com este numero de série."
            );
        }
        notebookExistente.setPatrimonio(notebookAtualizado.getPatrimonio());
        notebookExistente.setSerialNumber(notebookAtualizado.getSerialNumber());
        notebookExistente.setMarca(notebookAtualizado.getMarca());
        notebookExistente.setModelo((notebookAtualizado.getModelo()));
        notebookExistente.setProcessador((notebookAtualizado.getProcessador()));
        notebookExistente.setMemoria_ram(notebookAtualizado.getMemoria_ram());
        notebookExistente.setArmazenamento(notebookAtualizado.getArmazenamento());
        notebookExistente.setSistemaOperacional(
                notebookAtualizado.getSistemaOperacional());
        notebookExistente.setStatus(notebookAtualizado.getStatus());
        notebookExistente.setDataAquisicao(notebookAtualizado.getDataAquisicao());
        notebookExistente.setDataUltimaComunicacao(
                (notebookAtualizado.getDataUltimaComunicacao()));

        return notebookRepository.save(notebookExistente);
    }
}
