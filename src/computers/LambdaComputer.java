package computers;

import java.util.ArrayList;

import main.DistanceRanker;
import main.RankedData;

/*
 * This class works on the lambda vector, or the vector that stores a cluster center's equivalent of standard deviation.
 * It uses a numerical approximation to determine the a value very close to lambda much more quickly than it would 
 * take to find the actual value of lambda. The value this returns essentially close enough.
 */

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

	
	// The lambda computer takes in the sigmaVector, the piVector, and works on the lambdaVector
	public LambdaComputer(ArrayList<RankedData> sigmaVector,
			ArrayList<RankedData> piVector, ArrayList<Double> lambdaVector)
	{
		this.sigmaVector = sigmaVector;
		this.piVector = piVector;
		this.lambdaVector = lambdaVector;
		dRanker = new DistanceRanker();
	}

	// This method is a function that appears to take on the form of
	// f(x) = e^-x. There is a vertical asymptote at x = 0 and two horizontal 
	// asymptotes, where if sigma contains at least two different entries, then
	// this function as a solution of lambdaFunction(lambda,k) = 0 where lambda
	// is a positive real number (most likely not very large).
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

			outerSum += ti / denominatorOuter - innerSum;
		}

		double distanceSum = 0;

		for (int i = 0; i < piVector.size(); i++)
		{
			distanceSum += dRanker.getDistance(piVector.get(i),
					sigmaVector.get(k));
		}
		return outerSum - distanceSum;
	}

	// This method is a public way of easily computing lambda, and simply interfaces with
	// the more complicated method by using a default values.
	public double computeLambda(int k, double epsilon)
	{
		return computeLambda(MIN_VALUE, MAX_VALUE, k, epsilon);
	}

	
	// This method makes sure that a zero is contained between the left bound and right bound. 
	// If the right bound is past 1000, either a VERRY unlikely event has occurred, or it is the case
	// that lim x->infinty lambdaFunction(x,k)=0. 
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

	// This method actually does the numeric analysis for the lambda vector. The functional value of the 
	// middle of the left bound and right bound is computed. If the value is less than 0, then the right
	// bound moves to the middle, and a new middle is determined. If the value is greater than 0, then the
	// left bound moves to the center. Once the left bound and right bound are epsilon away from each other,
	// we return the middle value.
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
	
	// This method is called when it is time for the lambda vector to be worked on. 
	public void computeLVector()
	{
		for (int index = 0; index < sigmaVector.size(); index++)
		{
			lambdaVector.set(index, computeLambda(index, EPSILON));
			

		}
	}
}
