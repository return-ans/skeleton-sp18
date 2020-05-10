package hw3.hash;

import java.awt.Color;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdDraw;


public class SimpleOomage implements Oomage {
    protected int red;
    protected int green;
    protected int blue;

    private static final double WIDTH = 0.01;
    private static final boolean USE_PERFECT_HASH = true;

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            //compare with itself
            return true;
        }
        if (o == null || o.getClass() != this.getClass()) {
            //null or other class
            return false;
        }
        //cast to this class-->SimpleOomage
        SimpleOomage other = (SimpleOomage) o;
        return other.blue == this.blue && other.red == this.red && other.green == this.green;
    }

    @Override
    public int hashCode() {
        /*
        When the HashSet checks to see if ooA2 is there, it will first compute ooA2.hashCode,
        which for our code will be the default hashCode(), which is just the memory address.
         */
        //default hashCode just uses memory address
        if (!USE_PERFECT_HASH) {
            return red + green + blue;
        } else {
            int x = red / 5;
            int y = green / 5;
            int z = blue / 5;
            //calculate in 3-D coordination
            //each (r, g, b) represents a unique site in this 52x52x52 cube
            return (x) + (y * 52) + (z * 52 * 52);
        }
    }

    public SimpleOomage(int r, int g, int b) {
        if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255) {
            throw new IllegalArgumentException();
        }
        if ((r % 5 != 0) || (g % 5 != 0) || (b % 5 != 0)) {
            throw new IllegalArgumentException("red/green/blue values must all be multiples of 5!");
        }
        red = r;
        green = g;
        blue = b;
    }

    @Override
    public void draw(double x, double y, double scalingFactor) {
        StdDraw.setPenColor(new Color(red, green, blue));
        StdDraw.filledSquare(x, y, WIDTH * scalingFactor);
    }

    public static SimpleOomage randomSimpleOomage() {
        int red = StdRandom.uniform(0, 51) * 5;
        int green = StdRandom.uniform(0, 51) * 5;
        int blue = StdRandom.uniform(0, 51) * 5;
        return new SimpleOomage(red, green, blue);
    }

    public static void main(String[] args) {
        System.out.println("Drawing 4 random simple Oomages.");
        randomSimpleOomage().draw(0.25, 0.25, 1);
        randomSimpleOomage().draw(0.75, 0.75, 1);
        randomSimpleOomage().draw(0.25, 0.75, 1);
        randomSimpleOomage().draw(0.75, 0.25, 1);
    }

    public String toString() {
        return "R: " + red + ", G: " + green + ", B: " + blue;
    }
} 
