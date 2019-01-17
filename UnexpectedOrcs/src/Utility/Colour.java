package Utility;

public class Colour {

    public static final int MASK = 0xff;

    public static int colour(float r, float g, float b) {
        return colour(r, g, b, 0xff);
    }

    public static int colour(float r, float g, float b, float a) {
        int c = (int)a;
        c = (c << 8) + (int)r;
        c = (c << 8) + (int)g;
        c = (c << 8) + (int)b;

        return c;
    }

    public static int colour(float gray) {
        return colour(gray, 0xff);
    }

    public static int colour(float gray, float a) {
        return colour(gray, gray, gray, a);
    }


    public static int hsb(float h, float s, float b) {
        return hsb(h, s, b, 0xff);
    }

    public static int hsb(float hue, float saturation, float brightness, int a) {
        //hue (0 -> 360)
        //saturation and brightness (0 -> 1)
        int r = 0, g = 0, b = 0;
        if (saturation == 0) {
            r = g = b = (int) (brightness * 255.0f + 0.5f);
        } else {
            float h = (hue - (float) Math.floor(hue)) * 6.0f;
            float f = h - (float) java.lang.Math.floor(h);
            float p = brightness * (1.0f - saturation);
            float q = brightness * (1.0f - saturation * f);
            float t = brightness * (1.0f - (saturation * (1.0f - f)));
            switch ((int) h) {
                case 0:
                    r = (int) (brightness * 255.0f + 0.5f);
                    g = (int) (t * 255.0f + 0.5f);
                    b = (int) (p * 255.0f + 0.5f);
                    break;
                case 1:
                    r = (int) (q * 255.0f + 0.5f);
                    g = (int) (brightness * 255.0f + 0.5f);
                    b = (int) (p * 255.0f + 0.5f);
                    break;
                case 2:
                    r = (int) (p * 255.0f + 0.5f);
                    g = (int) (brightness * 255.0f + 0.5f);
                    b = (int) (t * 255.0f + 0.5f);
                    break;
                case 3:
                    r = (int) (p * 255.0f + 0.5f);
                    g = (int) (q * 255.0f + 0.5f);
                    b = (int) (brightness * 255.0f + 0.5f);
                    break;
                case 4:
                    r = (int) (t * 255.0f + 0.5f);
                    g = (int) (p * 255.0f + 0.5f);
                    b = (int) (brightness * 255.0f + 0.5f);
                    break;
                case 5:
                    r = (int) (brightness * 255.0f + 0.5f);
                    g = (int) (p * 255.0f + 0.5f);
                    b = (int) (q * 255.0f + 0.5f);
                    break;
            }
        }
        return colour(r, g, b, a);
    }

    public static int alpha(int c) {
        return (c >> 24) & MASK;
    }

    public static int red(int c) {
        return (c >> 16) & MASK;
    }

    public static int green(int c) {
        return (c >> 8) & MASK;
    }

    public static int blue(int c) {
        return c & MASK;
    }

}
