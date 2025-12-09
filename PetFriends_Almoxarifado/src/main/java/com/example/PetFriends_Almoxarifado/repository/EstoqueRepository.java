package com.example.PetFriends_Almoxarifado.repository;

import com.example.PetFriends_Almoxarifado.event.EstoqueItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EstoqueRepository extends JpaRepository<EstoqueItem, UUID> {
    Optional<EstoqueItem> findByProductId(UUID productId);
}
