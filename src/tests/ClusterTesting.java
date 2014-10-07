package tests;
import static org.junit.Assert.*;
import main.DistanceRanker;
import main.RankedData;

import org.junit.Test;


public class ClusterTesting {

	
	//TEST CASES FOR THE DISTANCE RANKER CLASS METHODS
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
	
	
	//TEST CASES FOR THE RANKED DATA CLASS METHODS
	@Test
	public void testGet()
	{
		RankedData data1 = new RankedData(new int[] {1,2,3,4,5});
		assertEquals(1,data1.get(0));
		assertEquals(2,data1.get(1));
		assertEquals(3,data1.get(2));
		assertEquals(4,data1.get(3));
		assertEquals(5,data1.get(4));
	}
	
	@Test public void testIsPositive()
	{
		RankedData data1 = new RankedData(new int[] {1,-2,3,-4,5});
		assertEquals(false, data1.isPositive(2));
		assertEquals(false, data1.isPositive(4));
		assertEquals(true, data1.isPositive(1));
		assertEquals(true, data1.isPositive(3));
		assertEquals(true, data1.isPositive(5));
	}
	
	@Test
	public void testIsIndexPositive()
	{
		RankedData data1 = new RankedData(new int[] {-1,2,-3,4,-5});
		assertEquals(true,data1.isIndexPositive(1));
		assertEquals(true,data1.isIndexPositive(3));
		assertEquals(false,data1.isIndexPositive(0));
		assertEquals(false,data1.isIndexPositive(2));
		assertEquals(false,data1.isIndexPositive(4));
	}
	
	@Test
	public void testGetPosition()
	{
		RankedData data1 = new RankedData(new int[] {5,3,4,1,2});
		assertEquals(3,data1.getPosition(1));
		assertEquals(4,data1.getPosition(2));
		assertEquals(1,data1.getPosition(3));
		assertEquals(2,data1.getPosition(4));
		assertEquals(0,data1.getPosition(5));
	}
	
	@Test
	public void testReset()
	{
		RankedData data1 = new RankedData(new int[] {1,2,3,4});
		RankedData data2 = data1;
		RankedData completeList = new RankedData(new int[]{1,2,3,4,5});
		data1.makeCompatableWithList(completeList);
		data1.reset();
		assertArrayEquals(data2.getListElements().toArray(), data1.getListElements().toArray());
	}
	
	@Test
	public void testCompatable()
	{
		RankedData data1 = new RankedData(new int[] {2,3,4,5,1});
		RankedData completeList = new RankedData(new int[] {1,2,4,3,5});
		assertEquals(true,data1.isCompatableWithList(completeList));
		assertEquals(true,completeList.isCompatableWithList(data1));
	}

	@Test
	public void testCompatable1Partial()
	{
		RankedData data1 = new RankedData(new int[] {2,3,4});
		RankedData completeList = new RankedData(new int[] {1,2,4,3,5});
		assertEquals(false,data1.isCompatableWithList(completeList));
	}
	
	@Test
	public void testCompatable2Partial()
	{
		RankedData completeList = new RankedData(new int[] {1,2,3,4,5});
		RankedData data1 = new RankedData(new int[] {1,2,3});
		assertEquals(false,data1.isCompatableWithList(completeList)); // data1 must always be compared to the complete list to function properly
	}
	
	@Test
	public void testMakeCompatable2Partial()
	{
		RankedData completeList = new RankedData(new int[] {1,2,3,4,5});
		RankedData partialList = new RankedData(new int[] {1,2,3,4});
		partialList.makeCompatableWithList(completeList);
		assertArrayEquals(completeList.getListElements().toArray(), partialList.getListElements().toArray());
	}
	
	
	@Test
	public void testGetSize()
	{
		RankedData completeList = new RankedData(new int[] {1,2,3,4,5});
		assertEquals(5, completeList.getSize());
	}

}
