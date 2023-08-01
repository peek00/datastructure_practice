import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int n;
    private WeightedQuickUnionUF ufTopBottom;
    private WeightedQuickUnionUF ufTopOnly;
    private boolean [][] opened;
    private int openedSites = 0;

    // creates n-by-n grid, with all sites initially blocked
    // Blocked grids are 0, open is 1
    public Percolation(int n){
        if (n <= 0) {
            throw new IllegalArgumentException("n must be greater than 0");
        }
        this.n = n;
        // Treat the top and bottom as an extra item
        ufTopBottom = new WeightedQuickUnionUF(n*n + 2); 
        ufTopOnly = new WeightedQuickUnionUF(n*n + 1);
        opened = new boolean[n+1][n+1]; // All false by default
        // Open the top and bottom
    }

    private void checkInput(int row, int col){
        if (row < 1 || col <1 || row > this.n || col > this.n) {
            throw new IllegalArgumentException("Row and column must be greater than 0");
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col){
        // The behaviour for both ufTopBottom and ufTopOnly is the same IF they are not bottom row
        // Bottom row, ufTopOnly does not connect to bottom
        this.checkInput(row, col);
        if (! this.opened[row][col]) {
            //Get index in 1d array
            int idx = (row - 1) * this.n + col;
            this.openedSites += 1;
            this.opened[row][col] = true; 
            // Given a row and column, get the index inside there
            // 1,1 -> idx 2, n+1,n+1 -> 1 + n*n
            
            // Check the adjacent and add them together
    
            // Check left and right
            if (col > 1 && isOpen(row, col-1)) {
                // Means left is valid, union them
                ufTopBottom.union(idx, idx - 1);
                ufTopOnly.union(idx, idx - 1);
            }
            if (col < this.n && isOpen(row, col+1)){
                // Means right is valid
                ufTopBottom.union(idx, idx + 1);
                ufTopOnly.union(idx, idx + 1);
            }
            // Check top and bottom
            if (row == 1){
                // Connect to virtual topsite
                ufTopBottom.union(idx, 0);
                ufTopOnly.union(idx, 0);
            }  else if (row > 1 && isOpen(row-1, col)){
                // Means top is valid
                ufTopBottom.union(idx, idx - this.n);
                ufTopOnly.union(idx, idx - this.n);
            }
    
            if (row == this.n) {
                // Connect to virtual bottom site
                ufTopBottom.union(idx, this.n*this.n + 1);
            } else if (row < this.n && isOpen(row+1, col) ){
                // Means bottom is valid, so only do for ufTop
                ufTopBottom.union(idx, idx + this.n);
                ufTopOnly.union(idx, idx + this.n);
            }
        }

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        this.checkInput(row, col);
        return opened[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col){
        this.checkInput(row, col);
        // At this point, the top row might NOT be connected yet
   
        // The row and col is considered full if it is connected to the TOP of the grid 
        int idx = (row - 1) * this.n + col;
        return ufTopOnly.find(0) == ufTopOnly.find(idx); // This checks top site and this side below
    }

    // returns the number of open sites
    public int numberOfOpenSites(){
        return this.openedSites;
    }

    // does the system percolate?
    public boolean percolates(){
        // Check if ANY of the top rows corrospond to any of the bottom rows
        // Connect all to the top side
        for (int i = 1; i <= this.n; i++){
            if (this.opened[1][i]) {
                ufTopBottom.union(0, i);
            }
        }

        // Connects to bottom side
        for (int i = 1; i <= this.n; i++){ // Starts from 1, goes to 3
            if (this.opened[this.n][i]) {
                ufTopBottom.union((this.n - 1 )*this.n + i, this.n*this.n+1);
            }
        }
        return ufTopBottom.find(0) == ufTopBottom.find(this.n*this.n + 1);
    }

    // test client (optional)
    public static void main(String[] args){
        int n = 5;
        Percolation p = new Percolation(n);
    
        // Test open, isOpen, isFull
        System.out.println("Initial status:");
        printMatrix(p.opened); // Print the initial grid status
    
        p.open(1, 1);
        p.open(1, 2);
        p.open(1, 2);
        p.open(2, 2);
        p.open(3, 2);
        p.open(3, 3);
        p.open(4, 3);
        p.open(5, 3);
        p.open(5, 5);
        p.open(2, 5);
        System.out.println("Is full " + p.isFull(5,3) );
        System.out.println("Is full " + p.isFull(2,5) );
        
        System.out.println("After opening (1,1), (2,2), (3,3):");
        System.out.println();
        printMatrix(p.opened); // Print the grid status after opening some sites
        
        System.out.println();
        System.out.println("(1,1) is open: " + p.isOpen(1, 1));
        System.out.println("(2,2) is full: " + p.isFull(2, 2));
    
        // Test numberOfOpenSites
        System.out.println("Number of open sites: " + p.numberOfOpenSites());
    
        // Test percolates
        System.out.println("Percolates: " + p.percolates());
    
    }

    private static void printMatrix(boolean[][] matrix) {
        int rows = matrix.length;
        int columns = matrix[0].length;

        for (int i = 1; i < rows; i++) {
            for (int j = 1; j < columns; j++) {
                if (matrix[i][j]){
                    System.out.print("T" + " ");
                } else {
                    System.out.print("F" + " ");
                }
            }
            System.out.println(); // Move to the next line after printing each row
        }
    }
}
