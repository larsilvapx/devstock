
package br.com.devstock.devstock.service;

import br.com.devstock.devstock.entity.Notebook;
import br.com.devstock.devstock.exception.ConflitoCadastroException;
import br.com.devstock.devstock.exception.RecursoNaoEncontradoException;
import br.com.devstock.devstock.repository.NotebookRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotebookServiceTest {

    @Mock
    private NotebookRepository notebookRepository;

    @InjectMocks
    private NotebookService notebookService;

    private Notebook criarNotebook() {
        Notebook notebook = new Notebook();
        notebook.setSerialNumber("SN-TEST-001");
        notebook.setMarca("Dell");
        notebook.setModelo("Latitude 5440");
        notebook.setStatus("DISPONIVEL");

        return notebook;
    }

    @Test
    void deveCadastrarNotebookComSucesso() {
        Notebook notebook = criarNotebook();

        when(notebookRepository.existsBySerialNumber("SN-TEST-001"))
                .thenReturn(false);

        when(notebookRepository.save(notebook))
                .thenReturn(notebook);

        Notebook resultado = notebookService.cadastrar(notebook);

        assertNotNull(resultado);
        assertEquals("SN-TEST-001", resultado.getSerialNumber());

        verify(notebookRepository).save(notebook);
    }

    @Test
    void naoDeveCadastrarNotebookComSerialDuplicado() {
        Notebook notebook = criarNotebook();

        when(notebookRepository.existsBySerialNumber("SN-TEST-001"))
                .thenReturn(true);

        assertThrows(
                ConflitoCadastroException.class,
                () -> notebookService.cadastrar(notebook)
        );

        verify(notebookRepository, never()).save(any(Notebook.class));
    }

    @Test
    void deveListarTodosOsNotebooks() {
        Notebook notebook = criarNotebook();

        when(notebookRepository.findAll())
                .thenReturn(List.of(notebook));

        List<Notebook> resultado = notebookService.listarTodos();

        assertEquals(1, resultado.size());
        assertEquals("SN-TEST-001", resultado.get(0).getSerialNumber());

        verify(notebookRepository).findAll();
    }

    @Test
    void deveBuscarNotebookPorId() {
        Notebook notebook = criarNotebook();

        when(notebookRepository.findById(1L))
                .thenReturn(Optional.of(notebook));

        Notebook resultado = notebookService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals("SN-TEST-001", resultado.getSerialNumber());

        verify(notebookRepository).findById(1L);
    }

    @Test
    void deveLancarExcecaoQuandoNotebookNaoForEncontrado() {
        when(notebookRepository.findById(9999L))
                .thenReturn(Optional.empty());

        RecursoNaoEncontradoException excecao = assertThrows(
                RecursoNaoEncontradoException.class,
                () -> notebookService.buscarPorId(9999L)
        );

        assertTrue(excecao.getMessage().contains("9999"));

        verify(notebookRepository).findById(9999L);
    }


    @Test
    void deveAtualizarNotebookComSucesso() {
        Notebook notebookExistente = criarNotebook();
        Notebook notebookAtualizado = criarNotebook();

        notebookAtualizado.setModelo("Latitude 5450");
        notebookAtualizado.setMemoriaRam("32GB");

        when(notebookRepository.findById(1L))
                .thenReturn(Optional.of(notebookExistente));

        when(notebookRepository.save(notebookExistente))
                .thenReturn(notebookExistente);

        Notebook resultado = notebookService.atualizar(
                1L,
                notebookAtualizado
        );

        assertEquals("Latitude 5450", resultado.getModelo());
        assertEquals("32GB", resultado.getMemoriaRam());

        verify(notebookRepository).save(notebookExistente);
    }

    @Test
    void naoDeveAtualizarNotebookComSerialJaCadastrado() {
        Notebook notebookExistente = criarNotebook();
        Notebook notebookAtualizado = criarNotebook();

        notebookAtualizado.setSerialNumber("SN-TEST-002");

        when(notebookRepository.findById(1L))
                .thenReturn(Optional.of(notebookExistente));

        when(notebookRepository.existsBySerialNumber("SN-TEST-002"))
                .thenReturn(true);

        assertThrows(
                ConflitoCadastroException.class,
                () -> notebookService.atualizar(1L, notebookAtualizado)
        );

        verify(notebookRepository, never()).save(any(Notebook.class));
    }

    @Test
    void deveExcluirNotebookExistente() {
        Notebook notebook = criarNotebook();

        when(notebookRepository.findById(1L))
                .thenReturn(Optional.of(notebook));

        assertDoesNotThrow(() -> notebookService.excluir(1L));

        verify(notebookRepository).delete(notebook);
    }

    @Test
    void naoDeveExcluirNotebookInexistente() {
        when(notebookRepository.findById(9999L))
                .thenReturn(Optional.empty());

        assertThrows(
                RecursoNaoEncontradoException.class,
                () -> notebookService.excluir(9999L)
        );

        verify(notebookRepository, never()).delete(any(Notebook.class));
    }

}
