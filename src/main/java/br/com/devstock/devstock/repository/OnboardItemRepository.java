package br.com.devstock.devstock.repository;

import br.com.devstock.devstock.entity.OnboardingItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OnboardItemRepository extends JpaRepository<OnboardingItem, Long> {

    List<OnboardingItem> findByOnboardingId(Long onboardingId);
}
