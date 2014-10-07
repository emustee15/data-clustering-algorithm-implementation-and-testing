package computers;

import java.util.ArrayList;

import main.*;

public class SigmaComputer
{
	ArrayList<RankedData> piVector;
	ArrayList<RankedData> sigmaVector;
	QVector qVector;

	public SigmaComputer(ArrayList<RankedData> piVector,
			ArrayList<RankedData> sigmaVector, QVector qVector)
	{
		this.piVector = piVector;
		this.sigmaVector = sigmaVector;
		this.qVector = qVector;
	}

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

}
