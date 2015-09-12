/******************************************************************************
 * Author:        Yuliia Synytsia
 * Written:       9/9/2015
 * Last updated:  9/11/2015
 * 
 * Programming Assignment 1: Percolation 
 * Compilation:  javac-algs4 PercolationStats.java
 * Execution:    java-algs4 PercolationStats.java
 * Dependencies: WeightedQuickUnionUF.java, StdStats.java, StdRandom.java
 *               Math.java, StdOut.java
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.lang.Math;


public class PercolationStats {
    private int row;
    private int column; 
    private int[][] gridTwoD; 
    private Percolation percolationForStats;
    private double openedSites;
    private double[] percolationTreshold;
    private int numberOfExperiment;
    
    public PercolationStats(int N, int T){     // perform T independent experiments on an N-by-N grid
         if (N <= 0 || T <= 0) {
            throw 
                new IllegalArgumentException("N, T must be bigger than 0.");
        }
         
         percolationTreshold = new double[T];
         numberOfExperiment = T;
         
        for(int i = 0; i < T; i++){                    // loop for repeating of experiments T-times
            gridTwoD = new int[N][N];                  // create N-by-N grid 
            percolationForStats = new Percolation(N);
            openedSites = 0.0;                         // initialize number of open sites
             for(int j = 0; j < N*N; j++ ){
                 row = StdRandom.uniform(N) + 1;       // choose random row
                 column = StdRandom.uniform(N) + 1;    // choose random column
                 if(gridTwoD[row-1][column-1] == 1) {  // if site is already open than skip
                     continue;
                 }
                 else{
                     gridTwoD[row-1][column-1] = 1;         // else open site
                     percolationForStats.open(row, column);
                     openedSites++;                         // increase number of open sites
                     if(percolationForStats.percolates()){  // if system already percolates
                         percolationTreshold[i] = openedSites/(N*N);  //calculate of the percolation threshold
                         break;
                     }
                     
                 }
                 
             }
        }
         
         
    }
    public double mean(){                      // sample mean of percolation threshold
        return StdStats.mean(percolationTreshold);
    }
    public double stddev(){                    // sample standard deviation of percolation threshold
        return StdStats.stddev(percolationTreshold);
    }
    public double confidenceLo(){              // low  endpoint of 95% confidence interval
        return mean() - (1.96 * Math.sqrt(stddev()))/Math.sqrt(numberOfExperiment);
    }
    public double confidenceHi() {             // high endpoint of 95% confidence interval
        return mean() + (1.96 * Math.sqrt(stddev()))/Math.sqrt(numberOfExperiment);
    }

    public static void main(String[] args){    // test client (described below)
        PercolationStats pstats; 
        if (args.length == 2){  
            pstats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));  // take N and T parameters from command line 
        }
        else 
        pstats = new PercolationStats(200, 100);  // if user doesn't put N and T parameters in command line let's take N = 200, T = 200
        StdOut.println("mean\t\t\t = " + pstats.mean());
        StdOut.println("stddev\t\t\t = " + pstats.stddev());
        StdOut.println("95% confidence interval\t = " + pstats.confidenceLo()
                + ", " + pstats.confidenceHi());
    }
  
}