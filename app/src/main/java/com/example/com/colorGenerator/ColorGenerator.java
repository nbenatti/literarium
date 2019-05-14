package app.literarium.colorGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class ColorGenerator {

    private final double GOLDEN_RATIO = 1.6180339887498948482;
    private final double s, v;

    public ColorGenerator(double s, double v) {
        if ((s < 0 || s > 1) || (v < 0 || v > 1)) {
            throw new GeneratorException("Illegal argument for constructor");
        }

        this.s = s;
        this.v = v;
    }

    public String[] getRandomColorStrings(int n) {
        if (n < 1) {
            throw new GeneratorException("Illegal argument for generating function");
        }

        List<String> list = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            list.add(getRandomColorString(i));
        }

        return list.toArray(new String[0]);
    }

    private String getRandomColorString(int i) {
        Random r = new Random();
        double h = (r.nextDouble() + i / GOLDEN_RATIO) % 1;
        return generateColor(h, v, s).toString();
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
}
