package br.com.devstock.devstock.repository;

import br.com.devstock.devstock.entity.Notebook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotebookRepository extends JpaRepository<Notebook, Long> {
    boolean existsBySerialNumber(String serialNumber);

    Optional<Notebook> findBySerialNumber(String serialNumber);
}


