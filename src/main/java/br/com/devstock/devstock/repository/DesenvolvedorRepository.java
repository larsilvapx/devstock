package br.com.devstock.devstock.repository;

import br.com.devstock.devstock.entity.Desenvolvedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DesenvolvedorRepository

    extends JpaRepository<Desenvolvedor, Long>{

    boolean existsByMatricula(String matricula);
    boolean existsByEmail(String email);
}
