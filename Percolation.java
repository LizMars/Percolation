import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
 
    private WeightedQuickUnionUF UF;
    private boolean [] grid;
    private int originN;
    private int gridN;
    
    public Percolation(int N) {     // create N-by-N grid, with all sites blocked
        originN = N;
        gridN = N*N + 2;
        UF = new WeightedQuickUnionUF(gridN);
        grid = new boolean[gridN];
        
        grid[0] = true; // Virtual Top site open
        grid[N*N+1] = true; // Virtual buttom site open
        
    }
    
    public void open(int i, int j) { // open site (row i, column j) if it is not open already
        checkBounds(i,j);
        if(isOpen(i,j) == false){
            int GridIndex = IndexConverter(i,j);
            grid[GridIndex] = true;
            OpenSiteConnection(i,j);
        }
    }
    

    public boolean isOpen(int i, int j){    // is site (row i, column j) open?
        checkBounds(i,j);
        return grid[IndexConverter(i,j)];
    }
       
    public boolean isFull(int i, int j){    // is site (row i, column j) full
         checkBounds(i,j);
        return UF.connected(0,IndexConverter(i,j));
    }
    
   
    public boolean percolates(){             // does the system percolate?
    
        return UF.connected(0,gridN-1);
    }

    private void checkBounds(int row, int column) {
        if (row <= 0 || row > originN) 
            throw new IndexOutOfBoundsException("row index i out of bounds");
        
        if (column <= 0 || column > originN) 
            throw new IndexOutOfBoundsException("column index j out of bounds");
    }
    
    private int IndexConverter(int row, int column){ // Convertor from 2D array to 1D
        return (row - 1) * originN + column;
    }
    
    private void OpenSiteConnection(int row, int column){
        int index = IndexConverter(row,column);
        
        if(row == 1){
            UF.union(0,index);
        }
        else{
            if(isOpen(row - 1,column)) {
                UF.union(index,IndexConverter(row - 1, column));
            }        
        }
        if(row == originN){
            UF.union(gridN - 1,index);
        }
        else{
            if(isOpen(row + 1, column)) {
                UF.union(index,IndexConverter(row + 1, column));
            }
        }
        if(column != 1){
            if(isOpen(row, column - 1)) {
                UF.union(index,IndexConverter(row, column - 1));
            }
        }
        if(column != originN){
            if(isOpen(row, column + 1)) {
                UF.union(index,IndexConverter(row, column + 1));
            }
        }
    }
   
    public static void main(String[] args){ // test client (optional)
  
      //Percolation test = new Percolation(10);
      
   }
}