package com.mrmenezes.novoinicio;

/**
 * Created by Mr Menezes on 02/06/2015.
 */
public class Tail {
    private int[][] tabuleiro;
    private int[][] peças;

    public Tail(){

    }

    public Tail(int[][] tabuleiro, int[][] peças) {
        this.tabuleiro = tabuleiro;
        this.peças = peças;
    }

    public int[][] getTabuleiro() {
        return tabuleiro;
    }

    public void setTabuleiro(int[][] tabuleiro) {
        this.tabuleiro = tabuleiro;
    }

    public int[][] getPeças() {
        return peças;
    }

    public void setPeças(int[][] peças) {
        this.peças = peças;
    }
}

