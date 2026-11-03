package br.com.devstock.devstock.repository;

import br.com.devstock.devstock.entity.Onboarding;
import br.com.devstock.devstock.enums.StatusOnboard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OnboardRepository extends JpaRepository<Onboarding, Long> {

    boolean existsByDesenvolvedorIdAndStatus(
            Long desenvolvedorId,
            StatusOnboard status
    );

}
