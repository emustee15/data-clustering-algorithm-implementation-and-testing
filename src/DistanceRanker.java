public class DistanceRanker
{

	public int getDistance(RankedData list, RankedData completeList)
	{
		
		if (!list.isCompatableWithList(completeList))
		{
			list.makeCompatableWithList(completeList);
		}
		
		if (list.getSize() == 0)
		{
			return 0;
		}

		int totalDistance = 0;

		for (int i = 0; i < list.getSize(); i++)
		{
			int neededNumberAtPosition = completeList.get(i);
			if (completeList.isIndexPositive(i) && list.isPositive(neededNumberAtPosition) || 
					(!completeList.isIndexPositive(i) && !list.isPositive(neededNumberAtPosition)))
			{
				int positionOfNeededNumber = list.getPosition(neededNumberAtPosition);
				totalDistance += (positionOfNeededNumber - i);
				list.swapPositions(i, positionOfNeededNumber);
			}
			else
			{
				int positionOfNeededNumber = list.getPosition(neededNumberAtPosition);
				int distanceToEnd = list.getSize() -1- i;
				int distanceToDesiredSpot = list.getSize() -1-positionOfNeededNumber;
				totalDistance += (distanceToEnd + distanceToDesiredSpot + 1);
				list.swapPositions(i, positionOfNeededNumber);
			}
		}

		list.reset();
		return totalDistance;
	}
	
}
