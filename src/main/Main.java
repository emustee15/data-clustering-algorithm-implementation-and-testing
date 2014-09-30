package main;

import java.util.ArrayList;

import computers.LambdaComputer;

public class Main
{
	public static void main(String args[])
	{
	//  DistanceRanker ranker = new DistanceRanker();
//		RankedData dataA = new RankedData(new int[] {-5,2,3,4,1});
//		RankedData dataB = new RankedData(new int[] {1,2,3,4,5});
//		System.out.println(ranker.getDistance(dataA, dataB));
		
		
//		FileLoader loader = new FileLoader();
//		
//		loader.loadFile("/Users/ericmustee/Desktop/file.txt");
//		RankedData masterRanking = new RankedData(new int[] {1,2,3,4,5});
		ArrayList<RankedData> piVector = new ArrayList<>();
		piVector.add(new RankedData(new int[] {1,2,3,4,5}));
		piVector.add(new RankedData(new int[] {2,1,3,4,5}));
		piVector.add(new RankedData(new int[] {3,2,1,4,-5}));
		piVector.add(new RankedData(new int[] {-1,-2,-3,-4,-5}));
		
		ArrayList<RankedData> sigmaVector = new ArrayList<>();
		sigmaVector.add(new RankedData(new int[] {2,1,3,4,5}));
		sigmaVector.add(new RankedData(new int[] {1,2,3,5,4}));
		
		ArrayList<Double> lambdaVector = new ArrayList<>();
		lambdaVector.add(0d);
		lambdaVector.add(0d);
		
		LambdaComputer lcompute = new LambdaComputer(sigmaVector, piVector, lambdaVector);
		
		double q = -2;;
		for (int i = 0; i < 100; i++)
		{
			q+= i/100d;
			System.out.println("lamda(" + q + ") = " + lcompute.lambdaFunction(q, 0));
		}
		
		System.out.println(lcompute.computeLambda(.01, 1, 0, .0000001));
		
	}
}
