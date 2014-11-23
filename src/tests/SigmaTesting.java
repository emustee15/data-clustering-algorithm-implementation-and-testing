package tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import main.QVector;
import main.RankedData;

import org.junit.Test;

import computers.SigmaComputer;


public class SigmaTesting {
	
	public static boolean contains(RankedData testData, ArrayList<RankedData> array)
	{
		for (RankedData rd : array)
		{
			if (rd.equals(testData))
			{
				return true;
			}
		}
		
		return false;
	}
	
	//Testing sigma {1,2,3,4,5} to see if it produces 5 new sigmas and if those values
	//are the expected values
	@Test
	public void testGetSigmaValues()
	{
		//Create piVector, sigmaVector, qVector for SigmaComputer constructor
		RankedData data1 = new RankedData(new int[]{1,2,3,4,5});
		RankedData data2 = new RankedData(new int[]{5,4,3,2,1});
		ArrayList<RankedData> piVector = new ArrayList<>();
		ArrayList<RankedData> sVector = new ArrayList<>();
		piVector.add(data1);
		piVector.add(data2);
		sVector.add(data1);
		SigmaComputer sigmaComputer = new SigmaComputer(piVector, sVector, new QVector(sVector.size(), piVector.size()));
		ArrayList<RankedData> sigmaValues = sigmaComputer.getSigmaValues(data1);
		
		assertEquals(6, sigmaValues.size());
		assertEquals(true, contains(new RankedData(new int[]{1,2,3,4,5}), sigmaValues));
		assertEquals(true, contains(new RankedData(new int[]{2,1,3,4,5}), sigmaValues));
		assertEquals(true, contains(new RankedData(new int[]{1,3,2,4,5}), sigmaValues));
		assertEquals(true, contains(new RankedData(new int[]{1,2,4,3,5}), sigmaValues));
		assertEquals(true, contains(new RankedData(new int[]{1,2,3,5,4}), sigmaValues));
		assertEquals(true, contains(new RankedData(new int[]{1,2,3,4,5}), sigmaValues));
	}
	
	//Test getSigmaValues with a sigma containing some negatives
	@Test
	public void testSigmaValuesWithNegatives()
	{
		RankedData data1 = new RankedData(new int[]{2,-1,3,-4,-5});
		RankedData data2 = new RankedData(new int[]{5,4,3,2,1});
		ArrayList<RankedData> piVector = new ArrayList<>();
		ArrayList<RankedData> sVector = new ArrayList<>();
		piVector.add(data1);
		piVector.add(data2);
		sVector.add(data1);
		SigmaComputer sigmaComputer = new SigmaComputer(piVector, sVector, new QVector(sVector.size(), piVector.size()));
		ArrayList<RankedData> sigmaValues = sigmaComputer.getSigmaValues(data1);
		
		assertEquals(6, sigmaValues.size());
		assertEquals(true, contains(new RankedData(new int[]{2,-1,3,-4,-5}), sigmaValues));
		assertEquals(true, contains(new RankedData(new int[]{-1,2,3,-4,-5}), sigmaValues));
		assertEquals(true, contains(new RankedData(new int[]{2,3,-1,-4,-5}), sigmaValues));
		assertEquals(true, contains(new RankedData(new int[]{2,-1,-4,3,-5}), sigmaValues));
		assertEquals(true, contains(new RankedData(new int[]{2,-1,3,-5,-4}), sigmaValues));
		assertEquals(true, contains(new RankedData(new int[]{2,-1,3,-4,5}), sigmaValues));
	}
	
	//Test getSigmaValues on a sigma with one element
	@Test
	public void testOneIntegerGetSigmaValues()
	{
		//Create piVector, sigmaVector, qVector for SigmaComputer constructor
		RankedData data1 = new RankedData(new int[]{1});
		RankedData data2 = new RankedData(new int[]{5,4,3,2,1});
		ArrayList<RankedData> piVector = new ArrayList<>();
		ArrayList<RankedData> sVector = new ArrayList<>();
		piVector.add(data1);
		piVector.add(data2);
		sVector.add(data1);
		SigmaComputer sigmaComputer = new SigmaComputer(piVector, sVector, new QVector(sVector.size(), piVector.size()));
		ArrayList<RankedData> sigmaValues = sigmaComputer.getSigmaValues(data1);
		
		assertEquals(2, sigmaValues.size());
		assertEquals(true, contains(new RankedData(new int[]{1}), sigmaValues));
		assertEquals(true, contains(new RankedData(new int[]{-1}), sigmaValues));
	}
	
	//Test if new sigma gets computed properly
	//In this example, the starting sigma is {1,2,3,4,-5}. It should converge
	//to the cluster center {1,2,3,4,5} for this test to pass
	@Test
	public void testComputeSigmaVector()
	{
		RankedData data1 = new RankedData(new int[]{1,2,3,4,5});
		RankedData data2 = new RankedData(new int[]{1,2,4,3,5});
		RankedData data3 = new RankedData(new int[]{2,1,3,4,5});
		RankedData data4 = new RankedData(new int[]{1,2,3,4,-5});
		ArrayList<RankedData> piVector = new ArrayList<>();
		ArrayList<RankedData> sVector = new ArrayList<>();
		piVector.add(data1);
		piVector.add(data1);
		piVector.add(data1);
		piVector.add(data1);
		piVector.add(data1);
		piVector.add(data2);
		piVector.add(data3);
		sVector.add(data4);
		QVector qVector = new QVector(sVector.size(), piVector.size());
		//fill qvector with default values
		for (int i = 0; i < sVector.size(); i++)
		{
			for (int j = 0; j < piVector.size(); j++)
			{
				qVector.set(i, j, 1.0d / sVector.size());
			}
		}
		SigmaComputer sigmaComputer = new SigmaComputer(piVector, sVector, qVector);
		sigmaComputer.computeSVector();
		sVector = sigmaComputer.getSigmaVector();
		
		assertEquals(1, sVector.size());
		assertEquals(true, contains(new RankedData(new int[]{1,2,3,4,5}), sVector));
		
	}

}
