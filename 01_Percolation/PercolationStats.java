import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] fracList;
    private double confidence_95 = 1.96;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) { 
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n and trials must be greater than 0");
        }

        this.fracList = new double[trials]; // Initalize the array

        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) { 
                // Randomly pick a site
                // If site is opened
                int row;
                int col;
                do {
                    row = StdRandom.uniformInt(1, n + 1);
                    col = StdRandom.uniformInt(1, n + 1);
                } while (p.isOpen(row, col));
                p.open(row, col);
            }          
            int openedSites = p.numberOfOpenSites();
            double frac = (double) openedSites / (n * n);
            // Add to list
            this.fracList[i] = frac;
        }

    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(this.fracList);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(this.fracList);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (this.confidence_95 * stddev() / Math.sqrt(this.fracList.length));

    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (this.confidence_95 * stddev() / Math.sqrt(this.fracList.length));
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, trials);
        System.out.println("mean                    = " + ps.mean());
        System.out.println("stddev                  = " + ps.stddev());
        System.out.println("95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
    }

}