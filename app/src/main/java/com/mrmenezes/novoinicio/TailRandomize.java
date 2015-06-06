package com.mrmenezes.novoinicio;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Mr Menezes on 02/06/2015.
 */
public class TailRandomize {
    private ArrayList<Tail> listaTail = new ArrayList<Tail>();

    public TailRandomize(){
        int[][] list = {{16, 88, 24, 48},{8, 24, 88, 56},{16, 88, 80, 96},{40, 64, 96, 24},{24, 16, 48, 56},{40, 80, 24, 88}};
        int[][] matriz = {{0, 0, 0, 0, 0, 0}, {1, 1, 1, 0, 0, 0}, {1, 1, 1, 1, 1, 1}, {0, 0, 1, 1, 1, 1}, {0, 0, 1, 1, 1, 0}, {0, 0, 0, 0, 0, 0}};
        listaTail.add(new Tail(matriz,list));
        int[][] list2 = {{96, 48, 8, 88},{24, 80, 40, 88},{88, 80, 16, 64},{72, 8, 40, 88},{88, 80, 16, 64},{72, 8, 40, 88}};
        int[][] matriz2 = {{0, 0, 0, 0, 0, 0}, {1, 1, 1, 0, 0, 0}, {1, 1, 1, 1, 1, 0}, {1, 0, 1, 1, 1, 0}, {0, 0, 0, 1, 1, 0}, {0, 0, 0, 1, 1, 0}};
        listaTail.add(new Tail(matriz2,list2));
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
