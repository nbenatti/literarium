package com.example.com.colorGenerator;

public final class ColorUtils {

    private static final String TAG = ColorUtils.class.getSimpleName();

    public static boolean similar(String s1, String s2) {
        return similar(new ColorTriplet(s1), new ColorTriplet(s2));
    }

    public static boolean similar(ColorTriplet c1, ColorTriplet c2) {
        return !difference(c1.getR(), c2.getR()) && !difference(c1.getG(), c2.getG()) && !difference(c1.getB(), c2.getB());
    }

    private static boolean difference(int v1, int v2) {
        return Math.abs(v1 - v2) >= 33;
    }

    public static String stringify(ColorTriplet c) {
        return "#" + convertToHex(c.getR()) + convertToHex(c.getG()) + convertToHex(c.getB());
    }

    private static String convertToHex(int n) {
        String s = Integer.toHexString(n);
        if (s.length() == 1) {
            s = "0" + s;
        }
        return s;
    }
}
