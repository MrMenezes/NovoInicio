package com.mrmenezes.novoinicio;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Mr Menezes on 02/06/2015.
 */
public class TailRandomize {
    private ArrayList<Tail> listaTail = new ArrayList<Tail>();

    public TailRandomize(){
        int[][] list = {{16, 88, 24, 48},{8, 24, 88, 56},{16, 88, 80, 96},{40, 64, 96, 24},{16, 88, 24, 48},{16, 88, 24, 48}};
        int[][] matriz = {{0, 0, 0, 0, 0, 0}, {1, 1, 1, 0, 0, 0}, {1, 1, 1, 1, 1, 1}, {0, 0, 1, 1, 1, 1}, {0, 0, 1, 1, 1, 0}, {0, 0, 0, 0, 0, 0}};
        listaTail.add(new Tail(matriz,list));
    }

    public Tail getRMatriz(){
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(listaTail.size());
        return this.listaTail.get(randomInt);
    }

    public int[] getRList(Tail tail){

        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(6);
        int[] lista = this.listaTail.get(this.listaTail.indexOf(tail)).getPeças()[randomInt];
        return lista;
    }
}
