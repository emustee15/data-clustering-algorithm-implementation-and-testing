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

	public double computeLambda(double minValue, double maxValue, int k,
			double epsilon)
	{

		double functionalMinValue = lambdaFunction(minValue, k);

		if (functionalMinValue < 0)
		{
			return computeLambda(minValue - .005, maxValue, k, epsilon);
		}

		double functionalMaxValue = lambdaFunction(maxValue, k);

		if (functionalMaxValue > 0)
		{
			return computeLambda(minValue + .005, maxValue, 0, epsilon);
		}

		double lambda = computeLambdaInternal(minValue, maxValue, 0, k, epsilon);
		return lambda;
	}

	private double computeLambdaInternal(double minValue, double maxValue,
			int depth, int k, double epsilon)
	{
		depth++;
		double functionalValue = lambdaFunction((minValue + maxValue) / 2d, k);

		if (Math.abs(maxValue - minValue) < epsilon)
		{
			return (minValue + maxValue) / 2d;
		}

		if (functionalValue < 0)
		{
			maxValue = (minValue + maxValue) / 2d;
		}
		else
		{
			minValue = (minValue + maxValue) / 2d;
		}

		return computeLambdaInternal(minValue, maxValue, depth, k, epsilon);

	}
}
