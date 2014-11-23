package computers;

import java.util.ArrayList;

import main.*;

public class SigmaComputer
{
	/*
	 * This class works on the sigma vector. The sigma vector contains all of the cluster centers. 
	 * The new cluster center is determined enumerating all potential cluster centers with distance
	 * 1 away from the current cluster center, and finding out which one has the highest potential
	 * for a match. The analysis is over when none of the cluster centers move to a new simga vector.
	 */
	
	ArrayList<RankedData> piVector;
	ArrayList<RankedData> sigmaVector;
	QVector qVector;
	
	// The sigma computer requires the piVector, and the qVector, and works on the sigma vector.
	public SigmaComputer(ArrayList<RankedData> piVector,
			ArrayList<RankedData> sigmaVector, QVector qVector)
	{
		this.piVector = piVector;
		this.sigmaVector = sigmaVector;
		this.qVector = qVector;
	}

	// This method returns all sigma values 1 away from a given ranking.
	// For instance, all rankings 1 away from {1,2,3,4} are
	// {2,1,3,4}, {1,3,2,4}, {1,2,4,3}, and {1,2,3,-4}. See DistanceRanker
	// for more details. 
	public ArrayList<RankedData> getSigmaValues(RankedData sigma)
	{
		ArrayList<RankedData> sigmaValues = new ArrayList<RankedData>();
		sigmaValues.add(sigma.clone());
		int sigmaSize = sigma.getSize();
		for (int i = 0; i < sigmaSize - 1; i++)
		{
			RankedData newSigma = sigma.clone();
			newSigma.swapPositions(i, i + 1);
			sigmaValues.add(newSigma);
		}
		
		RankedData negatedSigma = sigma.clone();
		negatedSigma.negate(sigmaSize - 1);
		sigmaValues.add(negatedSigma);

		return sigmaValues;
	}

	// This method calls the sigma computer to work on the sigma vector.
	public boolean computeSVector()
	{
		DistanceRanker distanceRanker = new DistanceRanker();
		boolean hasMoved = false;

		for (int s = 0; s < sigmaVector.size(); s++)
		{
			int minValueIndex = 0;
			double minValue = 0;

			ArrayList<RankedData> testValues = getSigmaValues(sigmaVector
					.get(s));

			for (int i = 0; i < testValues.size(); i++)
			{
				double sum = 0;
				for (int j = 0; j < piVector.size(); j++)
				{
					sum += qVector.get(s, j)
							* distanceRanker.getDistance(piVector.get(j),
									testValues.get(i));
				}
				if (i == 0)
				{
					minValue = sum;
				}
				
				if (sum < minValue)
				{
					minValue = sum;
					minValueIndex = i;
				}
			}
			
			sigmaVector.set(s, testValues.get(minValueIndex));
			sigmaVector.get(s).saveCurrentDataAsDefault();
			
			if (minValueIndex != 0)
			{
				hasMoved = true;
			}
		}

		return hasMoved;
	}
	
	public ArrayList<RankedData> getSigmaVector()
	{
		return sigmaVector;
	}

}
