package computers;

import java.util.ArrayList;

import main.QVector;
import main.RankedData;

public class CComputer
{
	ArrayList<Double> cVector;
	QVector qVector;
	int numberClusters, numRankings;
	ArrayList<RankedData> piVector;
	
	/*
	 * This class computes the values of the cVector, or the cluster weights. In its computations, it uses
	 * the qVector, the piVector, the cVector, and it needs to know the number of clsuters. 
	 */
	public CComputer(QVector qVector, ArrayList<RankedData> piVector, ArrayList<Double> cVector, int numberClusters)
	{
		this.qVector = qVector;
		this.numberClusters = numberClusters;
		this.cVector = cVector;
		this.piVector = piVector;
		this.numRankings = piVector.size();
	}
	
	// This method works on computing the cVector.
	public void computeCVector()
	{
		
		for (int iCluster = 0; iCluster < numberClusters ; iCluster++)
		{
			double sum = 0;
			for (int jRanking = 0; jRanking < numRankings; jRanking++)
			{
				sum += qVector.get(iCluster, jRanking);
			}
			cVector.set(iCluster, (1.0/numRankings)*sum);
		}
	}
}
