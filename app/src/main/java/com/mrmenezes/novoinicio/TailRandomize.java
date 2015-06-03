package com.mrmenezes.novoinicio;

import java.util.ArrayList;

/**
 * Created by Mr Menezes on 02/06/2015.
 */
public class TailRandomize {
    private ArrayList<Tail> listaTail = new ArrayList<Tail>();

    public TailRandomize(){
        int[][] list = {{16, 88, 24, 48},{16, 88, 24, 48},{16, 88, 24, 48},{16, 88, 24, 48},{16, 88, 24, 48},{16, 88, 24, 48}};
        int[][] matriz = {{0, 0, 0, 0, 0, 0}, {1, 1, 1, 0, 0, 0}, {1, 1, 1, 1, 1, 1}, {0, 0, 1, 1, 1, 1}, {0, 0, 1, 1, 1, 0}, {0, 0, 0, 0, 0, 0}};
        listaTail.add(new Tail(matriz,list));
    }

    public Tail getRMatriz(){
        return this.listaTail.get(listaTail.size()-1);
    }

    public int[] getRList(Tail tail){
        int[] lista = this.listaTail.get(this.listaTail.indexOf(tail)).getPeças()[1];
        return lista;
    }
}
