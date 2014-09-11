public class DistanceRanker
{

	public int getDistance(RankedData data1, RankedData data2)
	{
		if (data1.length == 0)
		{
			return 0;
		}

		int totalDistance = 0;

		for (int i = 0; i < data1.length; i++)
		{

			int neededNumberAtPosition = data2.get(i);
			if (data2.isIndexPositive(i) && data1.isPositive(neededNumberAtPosition) || 
					(!data2.isIndexPositive(i) && !data1.isPositive(neededNumberAtPosition)))
			{
				int positionOfNeededNumber = data1.getPosition(neededNumberAtPosition);
				totalDistance += (positionOfNeededNumber - i);
				data1.swapPositions(i, positionOfNeededNumber);
			}
			else
			{
				int positionOfNeededNumber = data1.getPosition(neededNumberAtPosition);
				int distanceToEnd = data1.length -1- i;
				int distanceToDesiredSpot = data1.length -1-positionOfNeededNumber;
				totalDistance += (distanceToEnd + distanceToDesiredSpot + 1);
				data1.swapPositions(i, positionOfNeededNumber);
			}
		}

		data1.reset();
		return totalDistance;
	}
}
