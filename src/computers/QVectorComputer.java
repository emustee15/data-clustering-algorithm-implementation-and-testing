package computers;

import main.*;

import java.util.ArrayList;


public class QVectorComputer
{
	
	QVector qVector;
	GFunctionComputer gFunction;
	public QVectorComputer()
	{
		gFunction = new GFunctionComputer();
	}
	
	public void computeQVector(ArrayList<RankedData> sigmaVector, ArrayList<RankedData> piVector, ArrayList<Double> lambdaVector, ArrayList<Double> cVector, int numberClusters)
	{
		qVector = new QVector(numberClusters, piVector.size());
		DistanceRanker dRanker = new DistanceRanker();
		int numberOfCompleteRankings = sigmaVector.get(0).getSize();
		for (int cluster = 0; cluster < numberClusters; cluster++)
		{
			for (int ranking = 0; ranking < piVector.size(); ranking++)
			{
				double numerator = 0;
				double denominator = 0;
				int numberOfPartialRankings = piVector.get(ranking).getSize();
				numerator = cVector.get(cluster);
				numerator *= Math.pow(Math.E, -lambdaVector.get(cluster)*dRanker.getDistance(piVector.get(ranking), sigmaVector.get(cluster)));
				numerator *= gFunction.compute(Math.pow(Math.E, -lambdaVector.get(cluster)),numberOfPartialRankings , numberOfCompleteRankings);
				
				for (int l = 1; l <= numberClusters; l++)
				{
					double sum = 1;
					sum *= cVector.get(l);
					sum *= Math.pow(Math.E, -lambdaVector.get(l) * dRanker.getDistance(piVector.get(ranking), sigmaVector.get(l)));
					sum *= gFunction.compute(Math.pow(Math.E, -lambdaVector.get(l)), piVector.get(ranking).getSize(), numberOfCompleteRankings);
					
					denominator+=sum;
				}
				qVector.set(cluster, ranking, numerator/denominator);
			}
		}
	}
	
	public QVector getQvector()
	{
		return qVector;
	}
	
	
	
	
}
