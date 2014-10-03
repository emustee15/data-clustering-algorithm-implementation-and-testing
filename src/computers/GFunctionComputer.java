package computers;
public class GFunctionComputer
{
	public double compute(double x, double t, int n)
	{

		double numerator = Math.pow((1 - x), t);
		double denominator = 1;

//		System.out.print("(1 - " + x +")^" + t +"/(");
		for (int i = 2*n; i >= 2*(n-t+1); i-=2)
		{
			denominator *= (1 - Math.pow(x, (i)));
//			System.out.print("(1 - " + x +"^" + ((i)) +")");
		}
//		System.out.println(")");
		return numerator / denominator;
	}
}
