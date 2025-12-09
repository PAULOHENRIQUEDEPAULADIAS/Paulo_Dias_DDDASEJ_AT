package com.example.PetFriends_Transporte.model;

import java.util.Objects;

public class CodigoRastreamento {

    private final String valor;

    public CodigoRastreamento(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("Código de rastreamento não pode ser vazio.");
        }

        if (!valor.matches("[A-Z0-9]{8,20}")) {
            throw new IllegalArgumentException("Código de rastreamento inválido.");
        }

        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CodigoRastreamento that = (CodigoRastreamento) o;
        return Objects.equals(valor, that.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }
}
