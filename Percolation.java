//import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdRandom;

public class Percolation {
	
	private boolean arr [][];
	private int size;
	private WeightedQuickUnionUF arr2;
	private int opensites;
	private int bottom;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) 
    {	if (n <= 0) throw new IllegalArgumentException("N must be > 0");
    	arr = new boolean [n][n];
    	size = n;
    	//0 and size^2 are the virtual top and bottom of the array
    	arr2  = new WeightedQuickUnionUF(size * size + 2);
    	opensites = 0;
    	bottom = size * size + 1;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) 
    {
    	if (isRealIndex(row, col) && isFull(row, col)) {
    		arr[row][col] = true;
    	} else {
    		return;
    	}
    	
    	int indx = (row * size) + col + 1;
    	//check left
    	if (col != 0) 
		{
			if (arr[row][col - 1]) 
			{
				arr2.union(indx, (indx - 1));
			}
		} 
    	
    	//check right
		if (col >=0 && col < size - 1) 
		{
			if (arr[row][col + 1]) 
			{
				arr2.union(indx, (indx + 1));
			}
		} 
		
		//check above
		if (row != 0) 
		{
			if (arr[row - 1][col]) 
			{
				arr2.union(indx, (indx - size));
			}
		}
		
		//check below
		if (row >=0 && row < size - 1) 
		{
			if (arr[row + 1][col]) 
			{
				arr2.union(indx, (indx + size));
			}
		}
		
		if (row == size - 1) {
			arr2.union(indx, bottom);
		}
		
		if (row == 0) {
			arr2.union(indx, 0);
		}
		
		
		
		
    	opensites++;
    }
    
    private void printArr() 
    {
    	
    	
    	for(int i=0; i < size; i++) {
    		System.out.print("[");
    		for(int j=0;j < size; j++) {
    			if (!arr[i][j]) {
    				if (j == size-1) {
    					System.out.print("□");
    				}else {
    					System.out.print("□ ");
    				}
    			}else {
    				if (j == size-1) {
    					System.out.print("■");
    				}else {
    					System.out.print("■ ");
    				}
    			}
    		}
    		System.out.println("]");
    	}
    	
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
    	if (isRealIndex(row, col)) {
    		return arr[row][col];
    	} else {
    		return false;
    	}
    	
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
    	if (isRealIndex(row, col)) {
    		return !arr[row][col];
    	} else {
    		return false;
    	}
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
    	return opensites;
    }
    
    private boolean isRealIndex(int row, int col) {
    	if (row > size || col > size) {
            throw new IllegalArgumentException("Index is out of bounds");
        }
    	return true;
    }

    // does the system percolate?
    public boolean percolates() {
    	
    	if (numberOfOpenSites() < size) {
    		return false;
    	}
    	
    	return arr2.find(0) == arr2.find(bottom);
    	
    	
    }

    // test client (optional)
    public static void main(String[] args ) 
    {
    	int col = -1;
    	int row = -1;
    	int n = 60;
    	Percolation myP = new Percolation(n);
    	myP.printArr();
    	System.out.print("\n\n");
    	
    	while(!myP.percolates()) {
    		col = StdRandom.uniformInt(n);
    		row = StdRandom.uniformInt(n);
    		myP.open(row, col);
    		myP.printArr();
    		System.out.print("\n\n");
    	}
    	System.out.println("Number of Sites: " + myP.numberOfOpenSites() + "/" + (myP.size * myP.size) );
    	
    	
    }
}