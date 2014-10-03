package computers;

import java.util.ArrayList;

import main.DistanceRanker;
import main.RankedData;

public class LambdaComputer
{
	ArrayList<RankedData> sigmaVector;
	ArrayList<RankedData> piVector;
	ArrayList<Double> lambdaVector;
	DistanceRanker dRanker;

	public final static double MIN_VALUE = .01d;
	public final static double MAX_VALUE = 1.5d;
	public final static double SMALLEST_EPSILON = .00000000000000011103d;
	public final static double EPSILON = 0.0000001d;

	public LambdaComputer(ArrayList<RankedData> sigmaVector,
			ArrayList<RankedData> piVector, ArrayList<Double> lambdaVector)
	{
		this.sigmaVector = sigmaVector;
		this.piVector = piVector;
		this.lambdaVector = lambdaVector;
		dRanker = new DistanceRanker();
	}

	public double lambdaFunction(double lambda, int k)
	{
		double outerSum = 0;
		int n = sigmaVector.get(0).getSize();
		for (int i = 0; i < piVector.size(); i++)
		{
			int ti = piVector.get(i).getSize();
			double denominatorOuter = Math.pow(Math.E, lambda) - 1;

			double innerSum = 0;

			for (int j = n - ti + 1; j <= n; j++)
			{
				double innerNumerator = 2 * j;
				double innerDenominator = Math.pow(Math.E, 2 * j * lambda) - 1;
				innerSum += innerNumerator / innerDenominator;
			}

			outerSum += ti / denominatorOuter + innerSum;
		}

		double distanceSum = 0;

		for (int i = 0; i < piVector.size(); i++)
		{
			distanceSum += dRanker.getDistance(piVector.get(i),
					sigmaVector.get(k));
		}
		return outerSum - distanceSum;
	}

	public double computeLambda(int k, double epsilon)
	{
		return computeLambda(MIN_VALUE, MAX_VALUE, k, epsilon);
	}

	public double computeLambda(double leftBound, double rightBound, int k,
			double epsilon)
	{

		if (rightBound>1000)
		{
			return Double.POSITIVE_INFINITY;
		}
		double functionalMinValue = lambdaFunction(leftBound, k);

		if (functionalMinValue < 0)
		{
			return computeLambda(leftBound - .005, rightBound, k, epsilon);
		}

		double functionalMaxValue = lambdaFunction(rightBound, k);

		if (functionalMaxValue > 0)
		{
			return computeLambda(leftBound, rightBound+5, 0, epsilon);
		}

		double lambda = computeLambdaInternal(leftBound, rightBound, k, epsilon);
		return lambda;
	}

	private double computeLambdaInternal(double leftBound, double rightBound,
		 int k, double epsilon)
	{
		double functionalValue = lambdaFunction((leftBound + rightBound) / 2d, k);

		if (Math.abs(rightBound - leftBound) < epsilon)
		{
			return (leftBound + rightBound) / 2d;
		}

		if (functionalValue < 0)
		{
			rightBound = (leftBound + rightBound) / 2d;
		}
		else
		{
			leftBound = (leftBound + rightBound) / 2d;
		}

		return computeLambdaInternal(leftBound, rightBound, k, epsilon);
	}
	
	public void computeLVector()
	{
		for (int index = 0; index < sigmaVector.size(); index++)
		{
			lambdaVector.set(index, computeLambda(index, EPSILON));
			

		}
	}
}
