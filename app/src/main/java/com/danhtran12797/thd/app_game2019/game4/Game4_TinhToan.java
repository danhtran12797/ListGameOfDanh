package com.danhtran12797.thd.app_game2019.game4;

import java.util.ArrayList;
import java.util.Collections;

public class Game4_TinhToan {
    private int a;
    private int b;
    private int temp;

    public Game4_TinhToan(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public int Tong() {
        return a + b;
    }

    public int Hieu() {
        return Math.max(a, b) - Math.min(a, b);
    }

    public int Tich() {
        return a * b;
    }

    public int LayB() {
        ArrayList<Integer> arrChiaHet = new ArrayList<>();
        for (int i = 1; i <= a; i++) {
            if (a % i == 0)
                arrChiaHet.add(i);
        }
        Collections.shuffle(arrChiaHet);
        temp = arrChiaHet.get(0);
        return arrChiaHet.get(0);
    }

    public int Thuong() {
        return a / temp;
    }

    public int LayKetQua(String kt) {
        if (kt.equals("+"))
            return Tong();
        if (kt.equals("-"))
            return Hieu();
        if (kt.equals("X"))
            return Tich();
        return Thuong();
    }

    public String Show(String kt) {
        if (kt.equals("+"))
            return a + " " + kt + " " + b;
        if (kt.equals("-"))
            return Math.max(a, b) + " " + kt + " " + Math.min(a, b);
        if (kt.equals("X"))
            return a + " " + kt + " " + b;
        return a + " " + kt + " " + LayB();
    }
}
