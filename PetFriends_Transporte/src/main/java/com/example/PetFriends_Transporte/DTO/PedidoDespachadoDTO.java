package com.example.PetFriends_Transporte.DTO;

import java.util.UUID;

public class PedidoDespachadoDTO {
    private UUID pedidoId;
    private String rastreamento;
    private EnderecoEntrega enderecoEntrega;

    public UUID getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(UUID pedidoId) {
        this.pedidoId = pedidoId;
    }

    public String getRastreamento() { return rastreamento; }

    public void setRastreamento(String rastreamento) {
        this.rastreamento = rastreamento;
    }

    public EnderecoEntrega getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public void setEnderecoEntrega(EnderecoEntrega enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }

    public static class EnderecoEntrega {
        private String rua;
        private String numero;
        private String cidade;
        private String uf;

        public String getRua() {
            return rua;
        }

        public void setRua(String rua) {
            this.rua = rua;
        }

        public String getNumero() { return numero; }

        public void setNumero(String numero) { this.numero = numero; }

        public String getCidade() {
            return cidade;
        }

        public void setCidade(String cidade) {
            this.cidade = cidade;
        }

        public String getUf() {
            return uf;
        }

        public void setUf(String uf) {
            this.uf = uf;
        }
    }
}
