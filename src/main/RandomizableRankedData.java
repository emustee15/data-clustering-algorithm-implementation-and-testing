package main;

import java.util.ArrayList;

public class RandomizableRankedData extends RankedData
{

	private int numberOfSwaps = 2;
	private boolean randomziedLenghts = false;
	private int probabilityOfSwap = 100;
	private int numberOfChildren = 20;
	private int numberOfRepeats = 1;
	private int currentIndex;
	private int swapTechnique = 0;

	public final static int DISTANCE_SWAP = 0;
	public final static int RANDOM_SWAP = 1;
	public final static int SYMMETRIC_SWAP = 2;

	public int getMinPartialRankings()
	{
		return minPartialRankings;
	}

	public void setMinPartialRankings(int minPartialRankings)
	{
		this.minPartialRankings = minPartialRankings;
	}

	public int getMaxPartialRankings()
	{
		return maxPartialRankings;
	}

	public void setMaxPartialRankings(int maxPartialRankings)
	{
		this.maxPartialRankings = maxPartialRankings;
	}

	private int minPartialRankings = 1;
	private int maxPartialRankings = 0;

	public RandomizableRankedData(int[] data)
	{
		super(data);
		maxPartialRankings = data.length;
	}

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

	public void setNumberOfRepeats(int repeats)
	{
		this.numberOfRepeats = repeats;
	}

	public int getNumberOfRepeats()
	{
		return numberOfRepeats;
	}

	public RankedData nextRankedData()
	{

		if (swapTechnique != SYMMETRIC_SWAP)
		{
			for (int i = 0; i < numberOfSwaps; i++)
			{
				int swapProb = (int) (Math.random() * 101);

				if (swapProb <= probabilityOfSwap)
				{
					if (swapTechnique == RANDOM_SWAP)
					{

						int aPosition = 0, bPosition = 0;

						while (aPosition == bPosition && data.size() != 1)
						{
							aPosition = (int) (Math.random() * data.size());
							bPosition = (int) (Math.random() * data.size());
						}

						swapPositions(aPosition, bPosition);
					}
					else if (swapTechnique == DISTANCE_SWAP)
					{
						int position = (int) (Math.random() * data.size());
						randomDistanceSwap(position);
					}
				}
			}
		}
		else
		{
			symmetricSwapper(currentIndex/numberOfRepeats);
		}
		int size = data.size();

		if (randomziedLenghts)
		{
			size = minPartialRankings + (int) (Math.random() * (maxPartialRankings - minPartialRankings + 1));
		}

		int[] rankings = new int[size];

		for (int i = 0; i < size; i++)
		{
			rankings[i] = data.get(i);
		}

		RankedData next = new RankedData(rankings);
		currentIndex++;
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
		if (maxPartialRankings > data.size())
		{
			maxPartialRankings = data.size();
		}
		System.out.println(data.size());
		minPartialRankings = (minPartialRankings > newData.getSize() ? newData.getSize() : minPartialRankings);
	}

	@Override
	public void saveCurrentDataAsDefault()
	{
		super.saveCurrentDataAsDefault();
	}

	public void setTechnique(int technique)
	{
		this.swapTechnique = technique;
	}

	public int getTechnique()
	{
		return swapTechnique;
	}

	public void symmetricSwapper(int index)
	{
		System.out.println("Sym: " + index);
		if (index == 0)
		{
			randomDistanceSwap(0);
			randomDistanceSwap(2);
		}
		else if (index == 1)
		{
			randomDistanceSwap(0);
		}
		else if (index == 2)
		{
			randomDistanceSwap(0);
			randomDistanceSwap(6);
		}
		else if (index == 3)
		{
			randomDistanceSwap(2);
		}
		else if (index == 5)
		{
			randomDistanceSwap(6);
		}
		else if (index == 6)
		{
			randomDistanceSwap(2);
			randomDistanceSwap(4);
		}
		else if (index == 7)
		{
			randomDistanceSwap(4);
		}
		else if (index == 8)
		{
			randomDistanceSwap(4);
			randomDistanceSwap(6);
		}
	}

	public void doneWithData()
	{
		currentIndex = 0;
	}

}
