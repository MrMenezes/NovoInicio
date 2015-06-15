package com.mrmenezes.novoinicio;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Mr Menezes on 02/06/2015.
 */
public class TailRandomize {
    public static int DIFICULDADE_NOOB = 1;

    public static int DIFICULDADE_NORMAL = 2;
    private ArrayList<Tail> listaTail3 = new ArrayList<Tail>();

    private ArrayList<Tail> listaTail4 = new ArrayList<Tail>();

    public TailRandomize() {
        int[][] list = {{16, 88, 24, 48}, {8, 24, 88, 56}, {16, 88, 80, 96}, {40, 64, 96, 24}, {24, 16, 72, 56}, {40, 80, 24, 88}};
        int[][] matriz = {{0, 0, 0, 0, 0, 0}, {1, 1, 1, 0, 0, 0}, {1, 1, 1, 1, 1, 1}, {0, 0, 1, 1, 1, 1}, {0, 0, 1, 1, 1, 0}, {0, 0, 0, 0, 0, 0}};
        listaTail4.add(new Tail(matriz, list));
        int[][] list2 = {{96, 48, 8, 88}, {8, 80, 40, 88}, {88, 80, 16, 64}, {72, 8, 40, 88}, {88, 80, 16, 64}, {72, 8, 40, 88}};
        int[][] matriz2 = {{0, 0, 0, 0, 0, 0}, {1, 1, 1, 0, 0, 0}, {1, 1, 1, 1, 1, 0}, {1, 0, 1, 1, 1, 0}, {0, 0, 0, 1, 1, 0}, {0, 0, 0, 1, 1, 0}};
        listaTail4.add(new Tail(matriz2, list2));


        int[][] list3 = {{16, 88, 24, 48}, {8, 24, 88, 56}, {16, 88, 80, 96}, {40, 64, 96, 24}, {24, 16, 72, 56}, {40, 80, 24, 88}};
        int[][] matriz3 = {{0, 0, 0, 0, 0, 0}, {1, 1, 1, 0, 0, 0}, {1, 1, 1, 1, 1, 1}, {0, 0, 1, 1, 1, 1}, {0, 0, 1, 1, 1, 0}, {0, 0, 0, 0, 0, 0}};
        listaTail3.add(new Tail(matriz, list));
        int[][] list4 = {{96, 48, 8, 88}, {8, 80, 40, 88}, {88, 80, 16, 64}, {72, 8, 40, 88}, {88, 80, 16, 64}, {72, 8, 40, 88}};
        int[][] matriz4 = {{0, 0, 0, 0, 0, 0}, {1, 1, 1, 0, 0, 0}, {1, 1, 1, 1, 1, 0}, {1, 0, 1, 1, 1, 0}, {0, 0, 0, 1, 1, 0}, {0, 0, 0, 1, 1, 0}};
        listaTail3.add(new Tail(matriz2, list2));

    }

    public Tail getRMatriz(int dificuldade) {
        if (dificuldade == 2) {
            Random randomGenerator = new Random();
            int randomInt = randomGenerator.nextInt(listaTail4.size());
            return this.listaTail4.get(randomInt);
        } else {
            Random randomGenerator = new Random();
            int randomInt = randomGenerator.nextInt(listaTail3.size());
            return this.listaTail3.get(randomInt);
        }
    }

    public int[] getRList(Tail tail, int dificuldade) {
        if (dificuldade == 2) {
            Random randomGenerator = new Random();
            int randomInt = randomGenerator.nextInt(6);
            int[] lista = this.listaTail4.get(this.listaTail4.indexOf(tail)).getPeças()[randomInt];
            return lista;
        } else {
            Random randomGenerator = new Random();
            int randomInt = randomGenerator.nextInt(6);
            int[] lista = this.listaTail3.get(this.listaTail3.indexOf(tail)).getPeças()[randomInt];
            return lista;
        }
    }
}
