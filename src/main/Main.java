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
		
		RankedData d1 = new RankedData(new int[] {1,2,3,4,5,6,7,8,9});
		RankedData d2 = new RankedData(new int[] {1,2,3,4,5,6,7,8,9});
		RankedData d3 = new RankedData(new int[] {1,2,3,4,5,6,7,8,9});
		int distance = ranker.getDistance(d1, d2);
		
		System.out.println(distance);
		
		RankedData newData = new RankedData(new int[] {});
		
		
		System.out.println(ranker.getDistance(d2, d3));
		System.out.println(ranker.getDistance(d3, d1));
		
		
		int maxDistance = 0;
		RankedData d1m,d2m,d3m;
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


						if (ranker.getDistance(d1, d3) > maxDistance)
						{
							maxDistance = ranker.getDistance(d1, d3);
							System.out.println(ranker.getDistance(d2, d3));
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
}
