
package br.com.devstock.devstock.service;

import br.com.devstock.devstock.entity.Kit;
import br.com.devstock.devstock.entity.KitItem;
import br.com.devstock.devstock.exception.ConflitoCadastroException;
import br.com.devstock.devstock.exception.RecursoNaoEncontradoException;
import br.com.devstock.devstock.repository.KitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class KitService {

    private final KitRepository kitRepository;

    public KitService(KitRepository kitRepository) {
        this.kitRepository = kitRepository;
    }

    @Transactional(readOnly = true)
    public List<Kit> listarTodos() {
        return kitRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Kit buscarPorId(Long id) {
        return kitRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Kit não encontrado com o ID: " + id
                        )
                );
    }

    @Transactional
    public Kit cadastrar(Kit kit) {

        if (kitRepository.existsByCodigo(kit.getCodigo())) {
            throw new ConflitoCadastroException(
                    "Já existe um kit cadastrado com este código."
            );
        }

        if (kit.getItens() != null) {
            for (KitItem item : kit.getItens()) {
                item.setKit(kit);
            }
        }

        return kitRepository.save(kit);
    }

    @Transactional
    public Kit atualizar(Long id, Kit kitAtualizado) {

        Kit kitExistente = buscarPorId(id);

        boolean codigoAlterado =
                !Objects.equals(kitExistente.getCodigo(), kitAtualizado.getCodigo());

        if (codigoAlterado
                && kitRepository.existsByCodigo(kitAtualizado.getCodigo())) {
            throw new ConflitoCadastroException(
                    "Já existe outro kit cadastrado com este código."
            );
        }

        kitExistente.setCodigo(kitAtualizado.getCodigo());
        kitExistente.setStatus(kitAtualizado.getStatus());
        kitExistente.setObservacao(kitAtualizado.getObservacao());

        /*
         * Nesta atualização, a lista recebida substitui os itens atuais.
         * Os itens anteriores que forem removidos da coleção serão excluídos
         * por causa de orphanRemoval = true na entidade Kit.
         */
        kitExistente.setItens(kitAtualizado.getItens());

        return kitRepository.save(kitExistente);
    }
}