package com.example.PetFriends_Almoxarifado.event;

import com.example.PetFriends_Almoxarifado.model.AggregateRoot;
import com.example.PetFriends_Almoxarifado.model.Quantidade; // ALTERAÇÃO: Importar value object para consistência.
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "estoque_item")
public class EstoqueItem extends AggregateRoot {

    @Id
    private final UUID id;
    private final UUID productId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "valor", column = @Column(name = "quantidade_total"))
    })
    private Quantidade quantidadeTotal;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "valor", column = @Column(name = "quantidade_reservada"))
    })
    private Quantidade quantidadeReservada;

    public EstoqueItem(UUID id, UUID productId, int quantidadeInicial) {
        this.id = id;
        this.productId = productId;
        this.quantidadeTotal = new Quantidade(quantidadeInicial);
        this.quantidadeReservada = new Quantidade(0);
    }

    public void reservar(UUID pedidoId, int quantidade) {
        Quantidade qtd = new Quantidade(quantidade);
        if (quantidadeDisponivel().getValor() < qtd.getValor()) {
            throw new IllegalStateException("Estoque insuficiente para reserva.");
        }
        this.quantidadeReservada = this.quantidadeReservada.adicionar(qtd.getValor());
        this.addEvent(new EstoqueReservadoEvent(this.id, pedidoId, this.productId, qtd.getValor()));
    }

    public void liberarReserva(UUID pedidoId, int quantidade) {
        Quantidade qtd = new Quantidade(quantidade);
        if (qtd.getValor() > this.quantidadeReservada.getValor()) {
            throw new IllegalArgumentException("Não é possível liberar mais do que foi reservado.");
        }
        this.quantidadeReservada = this.quantidadeReservada.remover(qtd.getValor());
        this.addEvent(new EstoqueLiberadoEvent(this.id, pedidoId, this.productId, qtd.getValor())); // ALTERAÇÃO: Emitir evento para notificar liberação (ex.: cancelamento de pedido).
    }

    public void debitar(UUID pedidoId, int quantidade) {
        Quantidade qtd = new Quantidade(quantidade);
        if (qtd.getValor() > this.quantidadeReservada.getValor()) {
            throw new IllegalStateException("Não é possível debitar sem reserva prévia.");
        }
        this.quantidadeTotal = this.quantidadeTotal.remover(qtd.getValor());
        this.quantidadeReservada = this.quantidadeReservada.remover(qtd.getValor());
        this.addEvent(new EstoqueDebitadoEvent(this.id, pedidoId, this.productId, qtd.getValor()));
    }

    public Quantidade quantidadeDisponivel() {
        return this.quantidadeTotal.remover(this.quantidadeReservada.getValor());
    }

    public UUID getId() { return id; }
    public UUID getProductId() { return productId; }
    public int getQuantidadeTotal() { return quantidadeTotal.getValor(); }
    public int getQuantidadeReservada() { return quantidadeReservada.getValor(); }
}