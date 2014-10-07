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
	
	
	public CComputer(QVector qVector, ArrayList<RankedData> piVector, ArrayList<Double> cVector, int numberClusters)
	{
		this.qVector = qVector;
		this.numberClusters = numberClusters;
		this.cVector = cVector;
		this.piVector = piVector;
		this.numRankings = piVector.size();
	}
	
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
	public ArrayList<Double> getCVector()
	{
		return cVector;
	}
}
