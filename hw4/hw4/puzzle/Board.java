package hw4.puzzle;

import edu.princeton.cs.algs4.Queue;

public class Board implements WorldState {
    private int size;
    private final int BLANK = 0;
    private int[][] thisTile; //immutable

    /**
     * Constructs a board from an N-by-N array of tiles where
     * tiles[i][j] = tile at row i, column j
     *
     * @param tiles
     */
    public Board(int[][] tiles) {
        this.size = tiles[0].length;
        thisTile = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                //making a copy for each value in tiles
                thisTile[i][j] = tiles[i][j];
            }
        }
    }

    /**
     * Returns value of tile at row i, column j (or 0 if blank)
     *
     * @param i
     * @param j
     * @return
     */
    public int tileAt(int i, int j) {
        if (!(i >= 0 && i < size && j >= 0 && j < size)) {
            throw new IndexOutOfBoundsException("out of bound");
        }
        return thisTile[i][j];

    }

    public int size() {
        return size;
    }

    /**
     * Returns neighbors of this board.
     * SPOILERZ: This is the answer.
     */
    @Override
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == BLANK) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = BLANK;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = BLANK;
                }
            }
        }
        return neighbors;
    }

    private int goalAt(int i, int j) {
        if (!(i >= 0 && i < size && j >= 0 && j < size)) {
            throw new IndexOutOfBoundsException("out of bound");
        }
        return i * size + j + 1;
    }

    public int hamming() {
        int ret = 0;
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size; j++) {
                if (tileAt(i, j) != goalAt(i, j)) {
                    ret++;
                }
            }
        }
        for (int j = 0; j < size - 1; j++) {
            if (tileAt(size - 1, j) != goalAt(size - 1, j)) {
                ret++;
            }
        }
        return ret;
    }

    private int goalRowIndex(int g) {
        if (g == BLANK) {
            return size - 1;
        }
        return (g - 1) / size;
    }

    private int goalColIndex(int g) {
        if (g == BLANK) {
            return size - 1;
        }
        return g % size - 1;
    }

    public int manhattan() {
        int ret = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                //to the current tile
                //if (i,j) is BLANK, skip it
                if (tileAt(i, j) != BLANK) {
                    int correctRow = goalRowIndex(tileAt(i, j));
                    int correctCol = goalColIndex(tileAt(i, j));
                    ret += Math.abs(i - correctRow) + Math.abs(j - correctCol);
                }
            }
        }
        return ret;
    }

    @Override
    public int estimatedDistanceToGoal() {
        // Estimated distance to goal. This method should
        // simply return the results of manhattan() when submitted to
        // Gradescope.
        return manhattan();
    }

    @Override
    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }
        if (y == null || getClass() != y.getClass()) {
            return false;
        }

        Board other = (Board) y;
        if (other.size() != this.size()) {
            return false;
        }
        for (int i = 0; i < this.size(); i++) {
            for (int j = 0; j < this.size(); j++) {
                if (this.tileAt(i, j) != other.tileAt(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * Returns the string representation of the board.
     * Uncomment this method.
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
