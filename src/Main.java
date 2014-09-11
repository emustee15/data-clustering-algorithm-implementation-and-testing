
public class Main
{
	public static void main(String args[])
	{
		DistanceRanker ranker = new DistanceRanker();
		RankedData dataA = new RankedData(new int[] {1,-3,2,4,5});
		RankedData dataB = new RankedData(new int[] {1,2,3,4,5});
		System.out.println(ranker.getDistance(dataA, dataB));
	}
}
