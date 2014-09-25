package computers;
public class GFunctionComputer
{
	public double compute(double x, double t, int n)
	{

		double numerator = Math.pow((1 - x), t);
		double denominator = 1;

		for (int i = 0; i <= t+1; i++)
		{
			denominator *= (1 - Math.pow(x, 2*n-i));
		}

		return numerator / denominator;
	}
}
