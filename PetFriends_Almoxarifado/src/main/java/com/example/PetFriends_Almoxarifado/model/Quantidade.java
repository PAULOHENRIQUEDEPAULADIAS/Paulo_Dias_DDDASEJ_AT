package com.example.PetFriends_Almoxarifado.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Quantidade {
    @Column(name = "valor")
    private final int valor;

    public Quantidade(int valor) {
        if (valor < 0) {
            throw new IllegalArgumentException("Quantidade não pode ser negativa.");
        }
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }

    public Quantidade adicionar(int quantidade) {
        return new Quantidade(this.valor + quantidade);
    }

    public Quantidade remover(int quantidade) {
        if (quantidade > this.valor) {
            throw new IllegalArgumentException("Quantidade insuficiente em estoque.");
        }
        return new Quantidade(this.valor - quantidade);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Quantidade)) return false;
        Quantidade that = (Quantidade) o;
        return valor == that.valor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }
}
