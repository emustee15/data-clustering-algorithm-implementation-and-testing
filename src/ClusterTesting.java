import static org.junit.Assert.*;

import org.junit.Test;


public class ClusterTesting {

	
	
	@Test
	public void testNormalCase() {
		//Distance Ranker is tested
		DistanceRanker r = new DistanceRanker();
		RankedData data1 = new RankedData(new int[] {1,2,3,4,5});
		RankedData data2 = new RankedData(new int[] {5,4,3,2,1});
		assertEquals(10, r.getDistance(data1, data2));
		assertEquals(10, r.getDistance(data2, data1));
		
	}
	@Test
	public void testOneNegativeIn1()
	{
		DistanceRanker r = new DistanceRanker();
		RankedData data1 = new RankedData(new int[] {-1,2,3,4,5});
		RankedData data2 = new RankedData(new int[] {5,4,3,2,1});
		assertEquals(11, r.getDistance(data1, data2));
		assertEquals(11, r.getDistance(data2, data1));
	}
	
	@Test
	public void testOneNegativeIn2()
	{
		DistanceRanker r = new DistanceRanker();
		RankedData data1 = new RankedData(new int[] {5,4,3,2,1});
		RankedData data2 = new RankedData(new int[] {-1,2,3,4,5});
		assertEquals(11, r.getDistance(data1, data2));
		assertEquals(11, r.getDistance(data2, data1));
	}
	
	@Test
	public void testBothAllNegative()
	{
		DistanceRanker r = new DistanceRanker();
		RankedData data1 = new RankedData(new int[] {-1,-2,-3,-4,-5});
		RankedData data2 = new RankedData(new int[] {-5,-4,-3,-2,-1});
		assertEquals(10,r.getDistance(data1, data2));
		assertEquals(10, r.getDistance(data2, data1));
	}
	@Test
	public void testFirstAllNegative()
	{
		DistanceRanker r = new DistanceRanker();
		RankedData data1 = new RankedData(new int[] {-5,-4,-3,-2,-1});
		RankedData data2 = new RankedData(new int[] {5,4,3,2,1});
		assertEquals(25,r.getDistance(data1, data2));
		assertEquals(25, r.getDistance(data2, data1));
	}

}
