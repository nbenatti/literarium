package literarium.colorGenerator;

public final class ColorTriplet {

    private final int r, g, b;
    private final String[] table = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};

    public ColorTriplet(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    @Override
    public String toString() {
        return "#" + convertToHex(r) + convertToHex(g) + convertToHex(b);
    }

    private String convertToHex(int n) {
        String s = "";

        while (n > 0) {
            s = table[n % 16] + s;
            n /= 16;
        }

        if (s.length() == 1) {
            s = "0" + s;
        }

        return s;
    }
}
