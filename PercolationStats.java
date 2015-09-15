/******************************************************************************
 * Author:        Yuliia Synytsia
 * Written:       9/9/2015
 * Last updated:  9/11/2015
 * 
 * Programming Assignment 1: Percolation 
 * Compilation:  javac-algs4 PercolationStats.java
 * Execution:    java-algs4 PercolationStats.java
 * Dependencies: WeightedQuickUnionUF.java, StdStats.java, StdRandom.java
 *               StdOut.java
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    private int row;
    private int column; 
    private Percolation percolationForStats;
    private double openedSites;
    private double[] percolationTreshold;
    private int numberOfExperiment;
    
    public PercolationStats(int N, int T) {     // perform T independent experiments on an N-by-N grid
         if (N <= 0 || T <= 0) {
            throw 
                new IllegalArgumentException("N, T must be bigger than 0.");
        }
         
         percolationTreshold = new double[T];
         numberOfExperiment = T;
         
        for (int i = 0; i < T; i++) {                    // loop for repeating of experiments T-times
            percolationForStats = new Percolation(N);
            openedSites = 0.0;                         // initialize number of open sites
             for (int j = 0; j < N*N; j++) {
                 row = StdRandom.uniform(N) + 1;       // choose random row
                 column = StdRandom.uniform(N) + 1;    // choose random column
                 if (percolationForStats.isOpen(row, column)) {  // if site is already open than skip
                     continue;
                 }
                 else {
                     percolationForStats.open(row, column);
                     openedSites++;                         // increase number of open sites
                     if (percolationForStats.percolates()) {  // if system already percolates
                         percolationTreshold[i] = openedSites / (double) (N*N);  //calculate of the percolation threshold
                         break;
                     }
                     
                 }
                 
             }
        }
        //percolationForStats = null;
         
         
    }
    public double mean() {                      // sample mean of percolation threshold
        return StdStats.mean(percolationTreshold);
    }
    public double stddev() {                    // sample standard deviation of percolation threshold
        return StdStats.stddev(percolationTreshold);
    }
    public double confidenceLo() {              // low  endpoint of 95% confidence interval
        return (mean() - ((1.96 * stddev())/Math.sqrt(numberOfExperiment)));
    }
    public double confidenceHi() {             // high endpoint of 95% confidence interval
        return (mean() + ((1.96 * stddev())/Math.sqrt(numberOfExperiment)));
    }

    public static void main(String[] args) {    // test client (described below)
        PercolationStats pstats; 
       
        pstats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));  
        
        StdOut.println("mean\t\t\t = " + pstats.mean());
        StdOut.println("stddev\t\t\t = " + pstats.stddev());
        StdOut.println("95% confidence interval\t = " + pstats.confidenceLo()
                + ", " + pstats.confidenceHi());
    }
  
}