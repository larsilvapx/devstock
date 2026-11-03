package br.com.devstock.devstock.repository;

import br.com.devstock.devstock.entity.OnboardingItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OnboardItemRepository extends JpaRepository<OnboardingItem, Long> {
}
