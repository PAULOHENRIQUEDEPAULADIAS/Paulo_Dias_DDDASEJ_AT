package com.example.PetFriends_Almoxarifado.event;

import com.example.PetFriends_Almoxarifado.model.DomainEvent;

import java.time.Instant;
import java.util.UUID;

public class EstoqueLiberadoEvent implements DomainEvent {
    private final UUID estoqueItemId;
    private final UUID pedidoId;
    private final UUID productId;
    private final int quantidade;
    private final Instant occurredOn = Instant.now();

    public EstoqueLiberadoEvent(UUID estoqueItemId, UUID pedidoId, UUID productId, int quantidade) {
        this.estoqueItemId = estoqueItemId;
        this.pedidoId = pedidoId;
        this.productId = productId;
        this.quantidade = quantidade;
    }

    @Override
    public String eventType() { return "Estoque Liberado"; }

    @Override
    public Instant occurredOn() { return occurredOn; }

    public UUID getEstoqueItemId() {
        return estoqueItemId;
    }

    public UUID getPedidoId() {
        return pedidoId;
    }

    public UUID getProductId() {
        return productId;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public Instant getOccurredOn() {
        return occurredOn;
    }
}