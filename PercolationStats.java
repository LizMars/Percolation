import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.lang.Math;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    private int row;
    private int column; 
    private boolean[][] gridTwoD; 
    private Percolation percolationForStats;
    private int openedSites;
    private double[] percolationTreshold;
    private int numberOfExperiment;
    public PercolationStats(int N, int T){     // perform T independent experiments on an N-by-N grid
         if (N <= 0 || T <= 0) {
            throw 
                new IllegalArgumentException("N, T must be bigger than 0.");
        }
         gridTwoD = new boolean[N][N];
         percolationForStats = new Percolation(N);
         percolationTreshold = new double[T];
         numberOfExperiment = T;
         
        for(int i = 0; i < T; i++){
             for(int j = 0; j < N*N; j++ ){
                 row = StdRandom.uniform(N) + 1;
                 column = StdRandom.uniform(N) + 1;
                 if(gridTwoD[row][column] == true) continue;
                 else{
                     gridTwoD[row][column] = true;
                     percolationForStats.open(row + 1, column + 1);
                     openedSites++;
                     if(percolationForStats.percolates()){
                         percolationTreshold[i] = openedSites/(N*N);
                         break;
                     }
                     
                 }
                 
             }
         gridTwoD = null;
         percolationForStats = null;
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
         PercolationStats pstats = 
            new PercolationStats(Integer.parseInt(args[0]), 
                Integer.parseInt(args[1]));
        StdOut.println("mean\t\t\t = " + pstats.mean());
        StdOut.println("stddev\t\t\t = " + pstats.stddev());
        StdOut.println("95% confidence interval\t = " + pstats.confidenceLo()
                + ", " + pstats.confidenceHi());
    }
  
}