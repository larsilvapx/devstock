package br.com.devstock.devstock.repository;

import br.com.devstock.devstock.entity.KitItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KitItemRepository extends JpaRepository<KitItem, Long> {

    List<KitItem> findByKitId(Long kitId);
}
