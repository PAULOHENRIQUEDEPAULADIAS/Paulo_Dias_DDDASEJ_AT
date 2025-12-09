package com.example.PetFriends_Transporte.repository;

import com.example.PetFriends_Transporte.model.Entrega;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EntregaRepository extends JpaRepository<Entrega, UUID> {

    Optional<Entrega> findByPedidoId(UUID pedidoId);
}
