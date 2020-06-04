import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    private double[][] energy;
    private int[][] color;
    private int[] flag;
    private int width;
    private int height;

    public SeamCarver(Picture picture) {
        int m = picture.height();
        int n = picture.width();
        color = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                color[i][j] = picture.getRGB(i, j);
            }
        }
    }

    public Picture picture() {
        removeVerticalSeam(findHorizontalSeam());
        Picture newPicture = new Picture(width(), height());
        for (int i = 0; i < height(); i++) {
            int idx = 0;
            for (int j = 0; j < width(); j++) {
                if (j != flag[i]) {
                    newPicture.setRGB(idx++, i, color[i][j]);
                }
            }
        }
        return newPicture;
    }

    public int width() {
        return this.width;
    }

    public int height() {
        return this.height;
    }

    private int getR(int x, int y) {
        int rgb = color[x][y];
        return (rgb >> 16) & 0xFF;
    }

    private int getG(int x, int y) {
        int rgb = color[x][y];
        return (rgb >> 8) & 0xFF;
    }

    private int getB(int x, int y) {
        int rgb = color[x][y];
        return (rgb >> 0) & 0xFF;
    }

    private int sub(int a, int b) {
        return a > b ? a - b : b - a;
    }

    private int rX(int x, int y) {
        int w = width();
        if (x == 0) {
            return sub(getR(x + 1, y), getR(w - 1, y));
        } else if (x == w - 1) {
            return sub(getR(0, y), getR(x - 1, y));
        }
        return sub(getR(x + 1, y), getR(x - 1, y));
    }

    private int gX(int x, int y) {
        int w = width();
        if (x == 0) {
            return sub(getG(x + 1, y), getG(w - 1, y));
        } else if (x == w - 1) {
            return sub(getG(0, y), getG(x - 1, y));
        }
        return sub(getG(x + 1, y), getG(x - 1, y));
    }

    private int bX(int x, int y) {
        int w = width();
        if (x == 0) {
            return sub(getB(x + 1, y), getB(w - 1, y));
        } else if (x == w - 1) {
            return sub(getB(0, y), getB(x - 1, y));
        }
        return sub(getB(x + 1, y), getB(x - 1, y));
    }

    private int rY(int x, int y) {
        int h = height();
        if (y == 0) {
            return sub(getR(x, y + 1), getR(x, h - 1));
        } else if (y == h - 1) {
            return sub(getR(x, 0), getR(x, y - 1));
        }
        return sub(getR(x, y + 1), getR(x, y - 1));
    }

    private int gY(int x, int y) {
        int h = height();
        if (y == 0) {
            return sub(getG(x, y + 1), getG(x, h - 1));
        } else if (y == h - 1) {
            return sub(getG(x, 0), getG(x, y - 1));
        }
        return sub(getG(x, y + 1), getG(x, y - 1));
    }

    private int bY(int x, int y) {
        int h = height();
        if (y == 0) {
            return sub(getB(x, y + 1), getB(x, h - 1));
        } else if (y == h - 1) {
            return sub(getB(x, 0), getB(x, y - 1));
        }
        return sub(getB(x, y + 1), getB(x, y - 1));
    }

    private int dX(int x, int y) {
        int rx = rX(x, y);
        int gx = gX(x, y);
        int bx = bX(x, y);
        return rx * rx + gx * gx + bx * bx;
    }

    private int dY(int x, int y) {
        int ry = rY(x, y);
        int gy = gY(x, y);
        int by = bY(x, y);
        return ry * ry + gy * gy + by * by;
    }

    public double energy(int x, int y) {
        int w = width();
        int h = height();
        if (x < 0 || x >= w || y < 0 || y >= h) {
            throw new IndexOutOfBoundsException("out of bound");
        }
        return dX(x, y) + dY(x, y);

    }

    public int[] findHorizontalSeam() {
        return null;

    }

    public int[] findVerticalSeam() {
        energy = new double[height()][width()];
        for (int i = 0; i < height(); i++) {
            for (int j = 0; j < width(); j++) {
                if (i == 0) {
                    energy[i][j] = energy(i, j);
                } else {
                    if (j == 0) {
                        double tmp = Math.min(energy[i - 1][j], energy[i - 1][j + 1]);
                        energy[i][j] = energy(i, j) + tmp;
                    } else if (j == width() - 1) {
                        double tmp = Math.min(energy[i - 1][j], energy[i - 1][j - 1]);
                        energy[i][j] = energy(i, j) + tmp;
                    } else {
                        double tmp = Math.min(energy[i - 1][j], energy[i - 1][j - 1]);
                        tmp = Math.min(tmp, energy[i - 1][j + 1]);
                        energy[i][j] = energy(i, j) + tmp;
                    }
                }
            }
        }
        int bottom = 0;
        double min = energy[height() - 1][0];
        for (int j = 1; j < width(); j++) {
            if (energy[height() - 1][j] < min) {
                min = energy[height() - 1][j];
                bottom = j;
            }
        }
        int len = height();
        int[] ret = new int[len];
        int idx = 0;
        ret[len - 1 - idx] = bottom;
        idx++;
        for (int i = height() - 1; i >= 1; i--) {
            if (bottom == 0) {
                if (energy[i - 1][bottom] > energy[i - 1][bottom + 1]) {
                    bottom = bottom + 1;
                }
            } else if (bottom == width() - 1) {
                if (energy[i - 1][bottom - 1] <= energy[i - 1][bottom]) {
                    bottom = bottom - 1;
                }
            } else {
                int tm = bottom;
                double tmp = energy[i - 1][tm - 1];
                tm = bottom - 1;
                if (energy[i - 1][bottom] < tmp) {
                    tmp = energy[i - 1][bottom];
                    tm = bottom;
                }
                if (energy[i - 1][bottom + 1] < tmp) {
                    tmp = energy[i - 1][bottom + 1];
                    tm = bottom + 1;
                }
                bottom = tm;
            }
            ret[len - 1 - idx] = bottom;
            idx++;
        }
        return ret;
    }

    public void removeHorizontalSeam(int[] seam) {

    }

    public void removeVerticalSeam(int[] seam) {
        flag = new int[height()];
        for (int i = 0; i < height(); i++) {
            flag[i] = seam[i];
        }
        this.width--;
    }
}
