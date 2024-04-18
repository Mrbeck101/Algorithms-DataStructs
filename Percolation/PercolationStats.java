import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
	private Percolation myP;
	private int size;
	private int numOfTrials;
	private double[] data;
	
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
    	myP = new Percolation(n);
    	size = n;
    	numOfTrials = trials;
    	data = new double[numOfTrials];
    }
    
    private void runPercolation() {
    	
    	for(int i = 0; i < numOfTrials; i++) {
	    	int col = 0;
	    	int row = 0;
	    	
	    	while(!myP.percolates()) {
	    		col = StdRandom.uniformInt(size);
	    		row = StdRandom.uniformInt(size);
	    		myP.open(row, col);
	    	}
	    	
	    	data[i] = (double) myP.numberOfOpenSites() / (size * size);
	    	myP = new Percolation(size);
    	}
    }

    // sample mean of percolation threshold
    public double mean() {	
    	return StdStats.mean(data);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
    	return StdStats.stddev(data);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
    	return mean() - ((1.96 * stddev()) / Math.sqrt(numOfTrials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
    	return mean() + ((1.96 * stddev()) / Math.sqrt(numOfTrials));
    }

   // test client (see below)
   public static void main(String[] args) {
	   System.out.print("Size of Grid:");
	   int nSize = StdIn.readInt();
	   System.out.print("\n\nNumber of Trials:");
	   int nTrial  = StdIn.readInt();
	   
	   PercolationStats ps = new PercolationStats(nSize, nTrial);
	   ps.runPercolation();
       String confidence = ps.confidenceLo() + ", " + ps.confidenceHi();
       System.out.println("\n\nmean                    = " + ps.mean());
       System.out.println("stddev                  = " + ps.stddev());
       System.out.println("95% confidence interval = " + confidence);
   }
    
    

}
