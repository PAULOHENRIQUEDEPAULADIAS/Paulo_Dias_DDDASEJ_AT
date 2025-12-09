package com.example.PetFriends_Transporte.model;

import java.util.Objects;

public class EnderecoEntrega {

    private final String rua;
    private String numero = "25";
    private final String cidade;
    private String uf = "SP";

    public EnderecoEntrega(String rua, String numero, String cidade, String uf) {
        if (rua == null || cidade == null || uf == null) {
            throw new IllegalArgumentException("Endereço inválido");
        }

        this.rua = rua;
        this.numero = numero;
        this.cidade = cidade;
        this.uf = uf;
    }

    public String rua() { return rua; }
    public String numero() { return numero; }
    public String cidade() { return cidade; }
    public String uf() { return uf; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EnderecoEntrega)) return false;
        EnderecoEntrega that = (EnderecoEntrega) o;
        return rua.equals(that.rua) &&
                numero.equals(that.numero) &&
                cidade.equals(that.cidade) &&
                uf.equals(that.uf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rua, numero, cidade, uf);
    }

    public String getRua() {
        return rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCidade() {
        return cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }
}