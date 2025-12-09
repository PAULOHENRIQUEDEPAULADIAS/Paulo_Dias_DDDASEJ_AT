package com.example.PetFriends_Almoxarifado.DTO;

import java.util.UUID;

public class PedidoCriadoDTO {
    private UUID pedidoId;
    private UUID productId;
    private int quantidade;

    public UUID getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(UUID pedidoId) {
        this.pedidoId = pedidoId;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}