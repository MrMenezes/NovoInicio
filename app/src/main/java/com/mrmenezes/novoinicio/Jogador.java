package com.mrmenezes.novoinicio;


public class Jogador implements Comparable<Jogador> {

    private int id;

    private int pontos;

    private String nome;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public int compareTo(Jogador jogador) {
        if (this.getPontos() < jogador.getPontos())
            return -1;
        else if (this.getPontos() > jogador.getPontos()) return 1;
        return 0;
    }
}
