package com.example.PetFriends_Almoxarifado.repository;

import com.example.PetFriends_Almoxarifado.model.OutboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OutboxRepository extends JpaRepository<OutboxEvent, UUID> {
    List<OutboxEvent> findTop100ByPublishedFalseOrderByOccurredOnAsc();
}
