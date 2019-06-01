package com.example.com.colorGenerator;

public final class ColorTriplet {

    private int r, g, b;

    public ColorTriplet(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public ColorTriplet(String s) {
        this.r = Integer.parseInt(s.substring(1, 3), 16);
        this.g = Integer.parseInt(s.substring(3, 5), 16);
        this.b = Integer.parseInt(s.substring(5, 7), 16);
    }

    public int getR() {
        return r;
    }

    public int getG() {
        return g;
    }

    public int getB() {
        return b;
    }

}
