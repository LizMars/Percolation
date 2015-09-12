/******************************************************************************
 * Author:        Yuliia Synytsia
 * Written:       9/9/2015
 * Last updated:  9/11/2015
 *
 * Programming Assignment 1: Percolation 
 * Compilation:  javac-algs4 Percolation.java
 * Execution:    java-algs4 Percolation.java
 * Dependencies: WeightedQuickUnionUF.java
 *
 ******************************************************************************/
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
 
    private WeightedQuickUnionUF UF;
    private boolean [] grid;
    private int originN;
    private int gridN;
    
    public Percolation(int N) {                   // create N-by-N grid, with all sites blocked
        originN = N;                              // make N visible for other methods
        gridN = N*N + 2;
        UF = new WeightedQuickUnionUF(gridN);
        grid = new boolean[gridN];                //create N-by-N boolean grid
        
        grid[0] = true;                           // Virtual Top site open
        grid[N*N+1] = true;                       // Virtual buttom site open
        
    }
    
    public void open(int i, int j) {              // open site (row i, column j) if it is not open already
        checkBounds(i,j);
        if(isOpen(i,j) == false){                 // if site is not open yet - open site else skip opening 
            int GridIndex = IndexConverter(i,j);
            grid[GridIndex] = true;               //open site
            OpenSiteConnection(i,j);              //connect site with neighbors
        }
    }
    

    public boolean isOpen(int i, int j){          // check is site (row i, column j) open?
        checkBounds(i,j);
        return grid[IndexConverter(i,j)];
    }
       
    public boolean isFull(int i, int j){          // is site (row i, column j) full
        checkBounds(i,j);
        return UF.connected(0,IndexConverter(i,j));
    }
    
   
    public boolean percolates(){                  // does the system percolate?
    
        return UF.connected(0,gridN-1);
    }

    private void checkBounds(int row, int column) {    // index checking
        if (row <= 0 || row > originN) 
            throw new IndexOutOfBoundsException("row index i out of bounds");
        
        if (column <= 0 || column > originN) 
            throw new IndexOutOfBoundsException("column index j out of bounds");
    }
    
    private int IndexConverter(int row, int column){                 // index convertor from 2D array to 1D
        return (row - 1) * originN + column;
    }
    
    private void OpenSiteConnection(int row, int column){            // site connection with neighbors or virtual top/bottom
        int index = IndexConverter(row,column);
        
        if(row == 1){                                                // if new site located on first row
            UF.union(0,index);                                       // union with virtual top site
        }
        else{
            if(isOpen(row - 1,column)) {                             // for sites that located on other rows (2,3,4...)
                UF.union(index,IndexConverter(row - 1, column));     // union with neibor that located above site's row 
            }        
        }
        if(row == originN){
            if(percolates() == false){                               // fix of backwash problem
                if(UF.connected(0,index)) UF.union(gridN - 1,index); // fix of backwash problem
                
            }
           
        }
        else{
            if(isOpen(row + 1, column)) {
                UF.union(index,IndexConverter(row + 1, column));     // union with neighbor that located under site's row
            }
        }
        if(column != 1){
            if(isOpen(row, column - 1)) {
                UF.union(index,IndexConverter(row, column - 1));     // union with neighbor that located to the left 
            }
        }
        if(column != originN){
            if(isOpen(row, column + 1)) {
                UF.union(index,IndexConverter(row, column + 1));     // union with neighbor that located to the right
            }
        }
    }
   
    public static void main(String[] args){           // test client (optional)
  
    
      
   }
}