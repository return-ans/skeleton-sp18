package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private double[] fraction; // the number of open sites of each time
    private int t;
    private int size;
    private double meanVal;
    private double stddevVal;
    private double lo;
    private double hi;

    private void simulation(Percolation perc, int times) {
        while (!perc.percolates()) {
            int row = StdRandom.uniform(size);
            int col = StdRandom.uniform(size);
            perc.open(row, col);
        }
        //cast to double!
        fraction[times] = (double) perc.numberOfOpenSites() / (size * size);
    }

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0) {
            throw new IllegalArgumentException(N + "is <=0 ");
        }
        if (T <= 0) {
            throw new IllegalArgumentException(T + "is <=0 ");
        }
        fraction = new double[T];
        t = T;
        size = N;
        for (int i = 0; i < T; i++) {
            simulation(pf.make(N), i);
        }
        meanVal = StdStats.mean(fraction);
        stddevVal = StdStats.stddev(fraction);
        lo = meanVal - 1.96 * stddev() / Math.sqrt(t);
        hi = meanVal + 1.96 * stddev() / Math.sqrt(t);
    }

    // sample mean of percolation threshold
    public double mean() {
        return meanVal;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddevVal;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return lo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return hi;
    }

}
