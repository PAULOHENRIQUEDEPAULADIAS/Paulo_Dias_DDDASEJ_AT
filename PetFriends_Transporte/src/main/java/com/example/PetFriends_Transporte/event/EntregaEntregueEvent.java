package com.example.PetFriends_Transporte.event;

import com.example.PetFriends_Transporte.model.DomainEvent;

import java.time.Instant;
import java.util.UUID;

public class EntregaEntregueEvent implements DomainEvent {
    private final UUID entregaId;
    private final UUID pedidoId;
    private final String codigoRastreamento;
    private final Instant occurredOn = Instant.now();

    public EntregaEntregueEvent(UUID entregaId, UUID pedidoId, String codigoRastreamento) {
        this.entregaId = entregaId;
        this.pedidoId = pedidoId;
        this.codigoRastreamento = codigoRastreamento;
    }

    @Override
    public String eventType() { return "EntregaEntregue"; }

    @Override
    public Instant occurredOn() { return occurredOn; }

    public UUID getEntregaId() {
        return entregaId;
    }

    public UUID getPedidoId() {
        return pedidoId;
    }

    public String getCodigoRastreamento() {
        return codigoRastreamento;
    }

    public Instant getOccurredOn() {
        return occurredOn;
    }
}