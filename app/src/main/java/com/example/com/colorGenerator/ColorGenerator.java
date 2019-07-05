package com.example.com.colorGenerator;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public final class ColorGenerator {

    private static final String TAG = ColorGenerator.class.getSimpleName();

    private final double GOLDEN_RATIO = 1.6180339887498948482;
    private final double s, v;

    public ColorGenerator(double s, double v) {
        if ((s < 0.5 || s > 1) || (v < 0.5 || v > 1)) {
            throw new GeneratorException("Illegal argument(s) for constructor");
        }

        this.s = s;
        this.v = v;
    }

    public String[] getRandomColorStrings(int n) {
        if (n < 1) {
            throw new GeneratorException("Illegal argument for generating function");
        }

        HashSet<String> set = new HashSet<>();
        while (set.size() < n) {
            set.add(getRandomColorString());
        }

        ArrayList<String> list = deleteSimilar(new ArrayList<>(set));

        return list.toArray(new String[0]);
    }

    private String getRandomColorString() {
        Random r = new Random();
        double h = (r.nextDouble() + 1.0 / GOLDEN_RATIO) % 1;
        return ColorUtils.stringify(generateColor(h, v, s));
    }

    private ColorTriplet generateColor(double h, double s, double v) {
        int h_i = (int) (h * 6);
        double f = h * 6 - h_i;
        double p = v * (1 - s);
        double q = v * (1 - f * s);
        double t = v * (1 - (1 - f) * s);

        double r, g, b;
        switch (h_i) {
            case 0:
                r = v;
                g = t;
                b = p;
                break;
            case 1:
                r = q;
                g = v;
                b = p;
                break;
            case 2:
                r = p;
                g = v;
                b = t;
                break;
            case 3:
                r = p;
                g = q;
                b = v;
                break;
            case 4:
                r = t;
                g = p;
                b = v;
                break;
            case 5:
                r = v;
                g = p;
                b = q;
                break;
            default:
                throw new GeneratorException("Unexpected value in unreachable statement");
        }

        return new ColorTriplet((int) (r * 256), (int) (g * 256), (int) (b * 256));
    }

    private ArrayList<String> deleteSimilar(ArrayList<String> list) {
        for (int i = 0; i < list.size();) {
            if (ColorUtils.similar(list.get(i), list.get((i + 1) % list.size()))) {
                list.remove((i + 1) % list.size());
                list.add(i + 1, getRandomColorString());
            } else {
                i++;
            }
        }
        return list;
    }
}
