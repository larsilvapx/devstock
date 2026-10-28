package br.com.devstock.devstock.repository;

import br.com.devstock.devstock.entity.Kit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KitRepository extends JpaRepository<Kit, Long> {
    boolean existsByCodigo(String codigo);
}
