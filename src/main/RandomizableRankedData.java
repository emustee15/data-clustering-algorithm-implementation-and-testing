package main;

import java.util.ArrayList;

public class RandomizableRankedData extends RankedData
{

	private int numberOfSwaps = 2;
	private boolean randomziedLenghts = false;
	private int probabilityOfSwap = 100;
	private int numberOfChildren = 20;

	public int getNumberOfChildren()
	{
		return numberOfChildren;
	}

	public void setNumberOfChildren(int numberOfChildren)
	{
		this.numberOfChildren = numberOfChildren;
	}

	public void setNumberOfSwaps(int numberOfSwaps)
	{
		this.numberOfSwaps = numberOfSwaps;
	}

	public void setRandomziedLenghts(boolean randomziedLenghts)
	{
		this.randomziedLenghts = randomziedLenghts;
	}

	public void setProbabilityOfSwap(int probabilityOfSwap)
	{
		this.probabilityOfSwap = probabilityOfSwap;
	}

	public RandomizableRankedData(int[] data)
	{
		super(data);
	}

	public RankedData nextRankedData()
	{

		for (int i = 0; i < numberOfSwaps; i++)
		{
			int swapProb = (int) (Math.random() * 101);

			if (swapProb <= probabilityOfSwap)
			{
				int aPosition = 0, bPosition = 0;

				while (aPosition == bPosition && data.size()!=1)
				{
					aPosition = (int) (Math.random() * data.size());
					bPosition = (int) (Math.random() * data.size());
				}

				swapPositions(aPosition, bPosition);
			}

		}
		int size = data.size();

		if (randomziedLenghts)
		{
			size = 1 + (int) (Math.random() * data.size());
		}

		int[] rankings = new int[size];

		for (int i = 0; i < size; i++)
		{
			rankings[i] = data.get(i);
		}

		RankedData next = new RankedData(rankings);

		reset();
		return next;
	}

	public int getNumberOfSwaps()
	{
		return numberOfSwaps;
	}

	public boolean isRandomziedLenghts()
	{
		return randomziedLenghts;
	}

	public int getProbabilityOfSwap()
	{
		return probabilityOfSwap;
	}
	
	public void setData(RankedData newData)
	{
		this.data = new ArrayList<>();
		
		for (int i = 0; i < newData.getSize(); i++)
		{
			this.data.add(newData.get(i));
		}
		saveCurrentDataAsDefault();
	}

}
