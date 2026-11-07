package br.com.devstock.devstock.repository;

import br.com.devstock.devstock.entity.Movimentacao;
import br.com.devstock.devstock.entity.Notebook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovimentacaoRepository extends
        JpaRepository<Movimentacao, Long> {

    List<Movimentacao> findByNotebookOrderByDataHoraDesc(Notebook notebook);
}
