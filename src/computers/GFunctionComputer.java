package computers;
public class GFunctionComputer
{
	

	// This class does not work on a vector, but is rather just a function of three variables.
	// This class is particularly important because it involves Dr. Rinker's research into 
	// Simplifying the G-Function through algebraic manipulation. 
	
	public double compute(double x, double t, int n)
	{
		
		
		double numerator = Math.pow((1 - x), t);
		double denominator = 1;

		for (int i = 2*n; i >= 2*(n-t+1); i-=2)
		{
			denominator *= (1 - Math.pow(x, (i)));
		}
		return numerator / denominator;
	}
}
