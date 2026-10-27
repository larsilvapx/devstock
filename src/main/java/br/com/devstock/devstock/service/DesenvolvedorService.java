
package br.com.devstock.devstock.service;

import br.com.devstock.devstock.entity.Desenvolvedor;
import br.com.devstock.devstock.exception.ConflitoCadastroException;
import br.com.devstock.devstock.exception.RecursoNaoEncontradoException;
import br.com.devstock.devstock.repository.DesenvolvedorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DesenvolvedorService {

    private final DesenvolvedorRepository desenvolvedorRepository;

    public DesenvolvedorService(
            DesenvolvedorRepository desenvolvedorRepository) {
        this.desenvolvedorRepository = desenvolvedorRepository;
    }

    public List<Desenvolvedor> listarTodos() {
        return desenvolvedorRepository.findAll();
    }

    public Desenvolvedor buscarPorId(Long id) {
        return desenvolvedorRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Desenvolvedor não encontrado: " + id
                        )
                );
    }

    public Desenvolvedor cadastrar(Desenvolvedor desenvolvedor) {

        if (desenvolvedorRepository.existsByMatricula(desenvolvedor.getMatricula())) {
            throw new ConflitoCadastroException(
                    "Já existe um desenvolvedor com esta matricula."
            );
        }
        if (desenvolvedorRepository.existsByEmail(desenvolvedor.getEmail())) {
            throw new ConflitoCadastroException(
                    "Já existe um desenvolvedor com este email"
            );
        }

        return desenvolvedorRepository.save(desenvolvedor);
    }
}
