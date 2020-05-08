package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private double[] fraction; // the number of open sites of each time
    private int times;
    private int size;
    private double meanVal;
    private double stddevVal;

    private void simulation(Percolation perc, int times) {
        while (!perc.percolates()) {
            int row = StdRandom.uniform(size);
            int col = StdRandom.uniform(size);
            if (!perc.isOpen(row, col)) {
                perc.open(row, col);
            }
        }
        fraction[times] = perc.numberOfOpenSites() / (size * size);
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
        times = T;
        size = N;
        for (int i = 0; i < T; i++) {
            simulation(pf.make(N), i);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        meanVal = StdStats.mean(fraction);
        return meanVal;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        stddevVal = StdStats.stddev(fraction);
        return stddevVal;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        double tmp = 1.96 * stddev() / Math.sqrt(times);
        return mean() - tmp;

    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        double tmp = 1.96 * stddev() / Math.sqrt(times);
        return mean() + tmp;
    }
}
