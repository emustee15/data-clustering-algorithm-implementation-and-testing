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
			int positionOfNeededNumber = data1.getPosition(neededNumberAtPosition);
			totalDistance += (positionOfNeededNumber-i);
			data1.swapPositions(i, positionOfNeededNumber);
		}
		
		data1.reset();
		return totalDistance;
	}
}
