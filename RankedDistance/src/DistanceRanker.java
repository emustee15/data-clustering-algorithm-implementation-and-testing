public class DistanceRanker
{

	public static void main(String args[])
	{
		DistanceRanker ranker = new DistanceRanker();
		RankedData dataA = new RankedData(new int[] {3,2,1});
		RankedData dataB = new RankedData(new int[] {1,2,3});
		
		
		System.out.println(ranker.getDistance(dataA, dataB));
	}
	
	
	
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
