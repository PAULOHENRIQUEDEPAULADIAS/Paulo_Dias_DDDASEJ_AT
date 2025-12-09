package com.example.PetFriends_Transporte.model;

import com.example.PetFriends_Transporte.event.EntregaDespachadaEvent;
import com.example.PetFriends_Transporte.event.EntregaEntregueEvent;
import com.example.PetFriends_Transporte.event.EntregaExtraviadaEvent;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "entregas")
public class Entrega extends AggregateRoot {

    @Id
    private final UUID id;
    private final UUID pedidoId;

    @Enumerated(EnumType.STRING)
    private EstadoEntrega estado;

    @Embedded
    private CodigoRastreamento codigoRastreamento;

    @Embedded
    private EnderecoEntrega enderecoEntrega;
    private LocalDateTime dataDespacho;
    private LocalDateTime dataEntrega;

    public Entrega(UUID id, UUID pedidoId, EnderecoEntrega enderecoEntrega) {
        this.id = id;
        this.pedidoId = pedidoId;
        this.enderecoEntrega = enderecoEntrega;
        this.estado = EstadoEntrega.AGUARDANDO_ENVIO;
    }

    public void despachar(CodigoRastreamento codigoRastreamento) {
        if (estado != EstadoEntrega.AGUARDANDO_ENVIO) {
            throw new IllegalStateException("Só é possível despachar pedidos que estão aguardando envio.");
        }
        this.codigoRastreamento = codigoRastreamento;
        this.estado = EstadoEntrega.EM_TRANSITO;
        this.dataDespacho = LocalDateTime.now();
        this.addEvent(new EntregaDespachadaEvent(this.id, this.pedidoId, this.codigoRastreamento.getValor()));
    }

    public void marcarComoEntregue() {
        if (estado != EstadoEntrega.EM_TRANSITO) {
            throw new IllegalStateException("Pedido só pode ser entregue se estiver em trânsito.");
        }
        this.estado = EstadoEntrega.ENTREGUE;
        this.dataEntrega = LocalDateTime.now();
        this.addEvent(new EntregaEntregueEvent(this.id, this.pedidoId, this.codigoRastreamento.getValor()));
    }

    public void marcarComoExtraviado() {
        if (estado != EstadoEntrega.EM_TRANSITO) {
            throw new IllegalStateException("Só pode extraviar um pedido que está em trânsito.");
        }
        this.estado = EstadoEntrega.EXTRAVIADO;
        this.addEvent(new EntregaExtraviadaEvent(this.id, this.pedidoId, this.codigoRastreamento.getValor()));
    }

    public EnderecoEntrega getEnderecoEntrega() { return enderecoEntrega; }

    public UUID getId() {
        return id;
    }

    public UUID getPedidoId() {
        return pedidoId;
    }

    public EstadoEntrega getEstado() {
        return estado;
    }

    public void setEstado(EstadoEntrega estado) {
        this.estado = estado;
    }

    public CodigoRastreamento getCodigoRastreamento() {
        return codigoRastreamento;
    }

    public void setCodigoRastreamento(CodigoRastreamento codigoRastreamento) {
        this.codigoRastreamento = codigoRastreamento;
    }

    public void setEnderecoEntrega(EnderecoEntrega enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }

    public LocalDateTime getDataDespacho() {
        return dataDespacho;
    }

    public void setDataDespacho(LocalDateTime dataDespacho) {
        this.dataDespacho = dataDespacho;
    }

    public LocalDateTime getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(LocalDateTime dataEntrega) {
        this.dataEntrega = dataEntrega;
    }
}