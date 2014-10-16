package computers;

import main.*;

import java.util.ArrayList;



public class QVectorComputer
{
	
	QVector qVector;
	GFunctionComputer gFunction;
	
	private ArrayList<RankedData> sigmaVector, piVector;
	private ArrayList<Double> lambdaVector, cVector;
	private int numberClusters;
	
	/*
	 * This class works on the qVector. The qVector is a probability vector that stores how likely it is for a single element in the 
	 * piVector is to be assigned with a single element in the sigma vector. The qVector computer requires all the vectors, and makes
	 * use of the GFunctionComputer. 
	 */
	public QVectorComputer(ArrayList<RankedData> sigmaVector, ArrayList<RankedData> piVector, ArrayList<Double> lambdaVector, ArrayList<Double> cVector, int numberClusters, QVector qVector)
	{
		gFunction = new GFunctionComputer();
		this.sigmaVector = sigmaVector;
		this.piVector = piVector;
		this.lambdaVector = lambdaVector;
		this.cVector = cVector;
		this.numberClusters = numberClusters;
		this.qVector = qVector;
	}
	
	// This method actually calls the qComputer to work on the qVector. 
	public void computeQVector()
	{
		DistanceRanker dRanker = new DistanceRanker();
		int numberOfCompleteRankings = sigmaVector.get(0).getSize();
		for (int jCluster = 0; jCluster < numberClusters; jCluster++)
		{
			for (int iRanking = 0; iRanking < piVector.size(); iRanking++)
			{
				double numerator = 0;
				double denominator = 0;
				int numberOfPartialRankings = piVector.get(iRanking).getSize();
				numerator = cVector.get(jCluster);
				numerator *= Math.pow(Math.E, -lambdaVector.get(jCluster)*dRanker.getDistance(piVector.get(iRanking), sigmaVector.get(jCluster)));
				numerator *= gFunction.compute(Math.pow(Math.E, -lambdaVector.get(jCluster)),numberOfPartialRankings , numberOfCompleteRankings);
				
				for (int l = 0; l < numberClusters; l++)
				{
					double sum = cVector.get(l);
					sum *= Math.pow(Math.E, -lambdaVector.get(l) * dRanker.getDistance(piVector.get(iRanking), sigmaVector.get(l)));
					sum *= gFunction.compute(Math.pow(Math.E, -lambdaVector.get(l)), numberOfPartialRankings, numberOfCompleteRankings);
					
					denominator+=sum;
				}
				qVector.set(jCluster, iRanking, numerator/denominator);
			}
		}
	}
	
	
	
	
	
}
