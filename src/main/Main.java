package main;

import java.util.ArrayList;

import computers.LambdaComputer;
import computers.SigmaComputer;

public class Main
{
	public static void main(String args[])
	{
		// DistanceRanker ranker = new DistanceRanker();
		// RankedData dataA = new RankedData(new int[] {-5,2,3,4,1});
		// RankedData dataB = new RankedData(new int[] {1,2,3,4,5});
		// System.out.println(ranker.getDistance(dataA, dataB));

		// FileLoader loader = new FileLoader();
		//
		// loader.loadFile("/Users/ericmustee/Desktop/file.txt");
		// RankedData masterRanking = new RankedData(new int[] {1,2,3,4,5});
		ArrayList<RankedData> piVector = new ArrayList<>();
		piVector.add(new RankedData(new int[] { 1, 2, 3, 4, 5 }));
		piVector.add(new RankedData(new int[] { 2, 1, 3, 4, 5 }));
		piVector.add(new RankedData(new int[] { 3, 2, 1, 4, -5 }));
		piVector.add(new RankedData(new int[] { -1, -2, -3, -4, -5 }));

		ArrayList<RankedData> sigmaVector = new ArrayList<>();
		sigmaVector.add(new RankedData(new int[] { 2, 1, 3, 4, 5 }));
		sigmaVector.add(new RankedData(new int[] { 1, 2, 3, 5, 4 }));
		ArrayList<Double> lambdaVector = new ArrayList<>();
		lambdaVector.add(0d);
		lambdaVector.add(0d);
		

//		LambdaComputer lcompute = new LambdaComputer(sigmaVector, piVector,
//				lambdaVector);
//		
//		lcompute.computeLambdaVector();
//		
//		for (Double d : lambdaVector)
//		{
//			System.out.println(d);
//		}
//		
//		SigmaComputer sCompute = new SigmaComputer(piVector, sigmaVector);
//		
//		ArrayList<RankedData> sigmaValues = sCompute.getSigmaValues(new RankedData(new int[]{1,2,3,4,5}));
//		
//		for (RankedData d : sigmaValues)
//		{
//			d.print();
//		}
		
	}
}
