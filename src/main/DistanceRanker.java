package main;

public class DistanceRanker
{
	
	/*
	 * This class gets the distance between a given complete ranking and some other
	 * partial ranking. First, the partial list is made compatible with the
	 * complete list. Then, we bubble elements from one list to make it look like the
	 * other list. The number of bubbles needed to make one list look like the other
	 * is the distance between two lists. 
	 */

	public int getDistance(RankedData list, RankedData completeList)
	{
		// Force the lists to be compatible. 
		if (!list.isCompatableWithList(completeList))
		{
			list.makeCompatableWithList(completeList);
		}
		
		// If the partial list is empty, then we assume it has minimal distance to the complete list,
		// which would be 0. 
		if (list.getSize() == 0)
		{
			return 0;
		}

		// Instantiate totalDistace so we can keep track of how far we have gone. 
		int totalDistance = 0;

		/* Keep bubbling elements until the lists are identical. A theorem dictates if we do the order
		 * from the left to the right, we will get the actual distance. Here is an example of the
		 * "bubbling" process:
		 *
		 *
		 * Partial	Complete	Steps
		 * 1,2,3	3,2,1		0
		 * 1,3,2	3,2,1		1
		 * 3,1,2	3,2,1		2
		 * 3,2,1	3,2,1		3
		 * 
		 * The lists are now equal and it took 3 steps. Two numbers that are negative need to be brought to the 
		 * end of the list and need to be made positive in an extra step. For example:
		 * 
		 * Partial	Complete	Steps
		 * 1,2,3	-1,2,3		0
		 * 2,1,3	-1,2,3		1
		 * 2,3,1	-1,2,3		2
		 * 2,3,-1	-1,2,3		3
		 * 2,-1,3	-1,2,3		4
		 * -1,2,3	-1,2,3		5
		 * 
		 * So the distance is 5 between {1,2,3} and {-1,2,3}.
		 * 
		 */
		for (int i = 0; i < list.getSize(); i++)
		{
			try
			{
			int neededNumberAtPosition = completeList.get(i);
			if (completeList.isIndexPositive(i) && list.isPositive(neededNumberAtPosition) || 
					(!completeList.isIndexPositive(i) && !list.isPositive(neededNumberAtPosition)))
			{
				int positionOfNeededNumber = list.getPosition(neededNumberAtPosition);
				totalDistance += (positionOfNeededNumber - i);
				list.push(i, positionOfNeededNumber);
			}
			else
			{
				int positionOfNeededNumber = list.getPosition(neededNumberAtPosition);
				int distanceToEnd = list.getSize() -1- i;
				int distanceToDesiredSpot = list.getSize() -1-positionOfNeededNumber;
				totalDistance += (distanceToEnd + distanceToDesiredSpot + 1);
				list.push(i, positionOfNeededNumber);
			}
			}
			catch (IndexOutOfBoundsException ex)
			{
				System.out.println("ERROR c: ");
				completeList.print();
				list.print();
			}
		}

		list.reset();
		return totalDistance;
	}

}
