package tests;

import computers.GFunctionComputer;
import static org.junit.Assert.*;

import org.junit.Test;


public class GFunctionTesting {
	
	//Test cases for G-Function computing
	@Test
	public void testStandardCase()
	{
		GFunctionComputer g = new GFunctionComputer();
		assertEquals(.27089947, g.compute(0.5, 2, 3), .00000001);
	}
	
	@Test
	public void testNegativeXCase()
	{
		GFunctionComputer g = new GFunctionComputer();
		assertEquals(2.43809524, g.compute(-0.5, 2, 3), .00000001);
	}
	
	@Test
	public void testXisZeroCase1()
	{
		GFunctionComputer g = new GFunctionComputer();
		assertEquals(1, g.compute(0, 2, 3), .00000001);
	}
	
	@Test
	public void testXisZeroCase2()
	{
		GFunctionComputer g = new GFunctionComputer();
		assertEquals(1, g.compute(0, 5, 10), .00000001);
	}
	
	@Test
	public void testXisZeroCase3()
	{
		GFunctionComputer g = new GFunctionComputer();
		assertEquals(1, g.compute(0, 20, 50), .00000001);
	}
	
	@Test
	public void test_N_equals_T_Case()
	{
		GFunctionComputer g = new GFunctionComputer();
		assertEquals(0.04537128, g.compute(0.5, 5, 5), .00000001);
	}
	
	@Test
	public void test_X_equals_1_Case()
	{
		GFunctionComputer g = new GFunctionComputer();
		assertEquals(Double.NaN, g.compute(1, 4, 5), .00000001);
	}

}
