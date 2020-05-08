package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int size;
    private int openCnt;
    private boolean[] open;
    private boolean[] connectTop;
    private boolean[] connectBottom;
    private WeightedQuickUnionUF uf;
    private boolean isPercolation;

    /**
     * Coordination
     */
    private int newIndex(int row, int col) {
        return row * size + col;
    }

    private void validate(int x, int y) {
        if (!(x >= 0 && x < size && y >= 0 && y < size)) {
            throw new IndexOutOfBoundsException("Out of bound.");
        }
    }

    /**
     * create N-by-N grid, with all sites initially blocked
     *
     * @param N
     */
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("N<=0");
        }
        size = N;
        openCnt = 0;
        isPercolation = false;
        open = new boolean[size * size];
        connectTop = new boolean[size * size];
        connectBottom = new boolean[size * size];
        for (int i = 0; i < size * size; i++) {
            open[i] = false;
            connectTop[i] = false;
            connectBottom[i] = false;
        }
        uf = new WeightedQuickUnionUF(size * size);
    }

    private boolean inBound(int row, int col) {
        return row >= 0 && row < size && col >= 0 && col < size;
    }

    /**
     * use one-D index
     *
     * @param row
     * @param col
     */
    public void open(int row, int col) {
        validate(row, col);
        if (isOpen(row, col)) {
            return;
        }
        openCnt++;
        int index = newIndex(row, col);
        open[index] = true;
        boolean top = false;
        boolean bottom = false;
        // on the top line, connect to top
        // on the bottom line, connect to bottom
        if (row == 0) {
            top = true;
        }
        if (row == size - 1) {
            bottom = true;
        }
        //check whether its open neighbor are connect and union with them
        //!!! using .find() to find the root

        int[] dx = {-1, 0, 0, 1};
        int[] dy = {0, -1, 1, 0};
        for (int k = 0; k < 4; k++) {
            int newX = row + dx[k];
            int newY = col + dy[k];
            if (inBound(newX, newY) && isOpen(newX, newY)) {
                //using .find() method  use its root to judge connection
                if (connectTop[uf.find(newIndex(newX, newY))]
                        || connectTop[uf.find(index)]) {
                    top = true;
                }
                if (connectBottom[uf.find(newIndex(newX, newY))]
                        || connectBottom[uf.find(index)]) {
                    bottom = true;
                }
                //then union with them
                uf.union(index, newIndex(newX, newY));
            }
        }
        //update this point connection

        //Union set :: find a root to represent a subset
        //to a non-root point, find its root to check its state
        //update the connection states
        connectTop[uf.find(index)] = top;
        connectBottom[uf.find(index)] = bottom;

        //Dynamic update the percolation state
        if (top && bottom) {
            isPercolation = true;
        }
    }

    public boolean isOpen(int row, int col) {
        if (row >= size || col >= size) {
            throw new IndexOutOfBoundsException("Out of bound!");
        }
        return open[newIndex(row, col)];
    }

    public boolean isFull(int row, int col) {
        if (row >= size || col >= size) {
            throw new IndexOutOfBoundsException("Out of bound!");
        }
        //As for non-root points, find their root which represents their states
        //both connect top and bottom
        int index = newIndex(row, col);
        //check their root
        return connectTop[uf.find(index)] && connectBottom[uf.find(index)];
    }

    public int numberOfOpenSites() {
        return openCnt;
    }

    public boolean percolates() {
        return isPercolation;
    }


    public static void main(String[] args) {
        Percolation per = new Percolation(10);
        per.open(0, 0);
        PercolationVisualizer.draw(per, 10);
        System.out.println(per.openCnt);
        per.open(0, 4);
        PercolationVisualizer.draw(per, 10);
        per.open(3, 7);
        PercolationVisualizer.draw(per, 10);
        per.open(3, 9);
        PercolationVisualizer.draw(per, 10);
        per.open(0, 5);
        PercolationVisualizer.draw(per, 10);
        per.open(2, 2);

    }

}
