package main;

import java.util.ArrayList;

import computers.LambdaComputer;
import computers.SigmaComputer;

// This is an experimental class not to be instantiated. 

@Deprecated
public class Main
{
	public static void main(String args[])
	{
		DistanceRanker ranker = new DistanceRanker();
		
		RankedData d1 = new RankedData(new int[] {1,2,3,4,5});
		RankedData d2 = new RankedData(new int[] {5,2,3,4,1});
		RankedData d3 = new RankedData(new int[] {3,4,5,1,2});
		int distance = ranker.getDistance(d1, d2);
		
		System.out.println(distance);
		
		RankedData newData = new RankedData(new int[] {});
		
		
		System.out.println(ranker.getDistance(d2, d3));
		System.out.println(ranker.getDistance(d3, d1));
		
		
		for (int i = 0; i < 100000; i++)
		{
			d1.randomize();
			d2.randomize();
			d3.randomize();
			
			if (ranker.getDistance(d2, d3) == ranker.getDistance(d2, d1))
			{
				if (ranker.getDistance(d2, d3) == ranker.getDistance(d1, d3))
				{
					if (ranker.getDistance(d1, d3) != 0)
					{
						System.out.println(ranker.getDistance(d2, d3));
						System.out.println(ranker.getDistance(d3, d1));
						System.out.println(ranker.getDistance(d1, d2));
						d1.print();
						d2.print();
						d3.print();
						System.out.println("----------------------------------------------");

						
						
					}
				}
				
			}
				
			
		}
		
	}
}
