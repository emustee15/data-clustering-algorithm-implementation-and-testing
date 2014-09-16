import static org.junit.Assert.*;

import org.junit.Test;


public class ClusterTesting {

	
	
	@Test
	public void testNormalCase() {
		//Distance Ranker is tested
		DistanceRanker r = new DistanceRanker();
		RankedData data1 = new RankedData(new int[] {1,2,3,4,5});
		RankedData completeList = new RankedData(new int[] {5,4,3,2,1});
		assertEquals(10, r.getDistance(data1, completeList));
		assertEquals(10, r.getDistance(completeList, data1));
		
	}
	@Test
	public void testOneNegativeIn1()
	{
		DistanceRanker r = new DistanceRanker();
		RankedData data1 = new RankedData(new int[] {-1,2,3,4,5});
		RankedData completeList = new RankedData(new int[] {5,4,3,2,1});
		assertEquals(11, r.getDistance(data1, completeList));
		assertEquals(11, r.getDistance(completeList, data1));
	}
	
	@Test
	public void testOneNegativeIn2()
	{
		DistanceRanker r = new DistanceRanker();
		RankedData data1 = new RankedData(new int[] {5,4,3,2,1});
		RankedData completeList= new RankedData(new int[] {-1,2,3,4,5});
		assertEquals(11, r.getDistance(data1, completeList));
		assertEquals(11, r.getDistance(completeList, data1));
	}
	
	@Test
	public void testBothAllNegative()
	{
		DistanceRanker r = new DistanceRanker();
		RankedData data1 = new RankedData(new int[] {-1,-2,-3,-4,-5});
		RankedData completeList = new RankedData(new int[] {-5,-4,-3,-2,-1});
		assertEquals(10,r.getDistance(data1, completeList));
		assertEquals(10, r.getDistance(completeList, data1));
	}
	@Test
	public void testFirstAllNegative()
	{
		DistanceRanker r = new DistanceRanker();
		RankedData data1 = new RankedData(new int[] {-5,-4,-3,-2,-1});
		RankedData completeList = new RankedData(new int[] {5,4,3,2,1});
		assertEquals(25,r.getDistance(data1, completeList));
		assertEquals(25, r.getDistance(completeList, data1));
	}
	
	@Test
	public void testCompatable2Partial()
	{
		RankedData completeList = new RankedData(new int[] {1,2,3,4,5});
		RankedData data1 = new RankedData(new int[] {1,2,3});
		assertEquals(false,data1.isCompatableWithList(completeList)); // data1 must always be compared to the complete list to function properly
	}
	
	@Test
	public void testCompatable1Partial()
	{
		RankedData data1 = new RankedData(new int[] {2,3,4});
		RankedData completeList = new RankedData(new int[] {1,2,4,3,5});
		assertEquals(false,data1.isCompatableWithList(completeList));
	}
	
	@Test
	public void testCompatable()
	{
		RankedData data1 = new RankedData(new int[] {2,3,4,5,1});
		RankedData completeList = new RankedData(new int[] {1,2,4,3,5});
		assertEquals(true,data1.isCompatableWithList(completeList));
	}

}
