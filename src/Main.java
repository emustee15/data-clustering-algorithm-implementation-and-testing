
public class Main
{
	public static void main(String args[])
	{
		DistanceRanker ranker = new DistanceRanker();
		RankedData dataA = new RankedData(new int[] {3,2,1});
		RankedData dataB = new RankedData(new int[] {1,2,3});
		
		
		System.out.println(ranker.getDistance(dataA, dataB));
	}
}
