package main;

import java.util.ArrayList;

public class RandomizableRankedData extends RankedData
{
	/*
	 * This class is a single ranking that represents a single cluster center in the randomizeable 
	 * ranked data. This cluster can create individual rankings with similar characteristics to this
	 * own cluster. In a way, this is a factory for creating similar, but not the same rankings. 
	 */
	
	private static final long serialVersionUID = 1L;
	private int numberOfSwaps = 2;
	private boolean randomziedLenghts = false;
	private int probabilityOfSwap = 100;
	private int numberOfChildren = 20;
	private int numberOfRepeats = 1;
	private int currentIndex;
	private int swapTechnique = 0;
	private static int[] symmetricWeightings = {1,2,1,2,8,2,1,2,1};

	// This may be better of using the strategy pattern in the future. 
	public final static int DISTANCE_SWAP = 0;
	public final static int RANDOM_SWAP = 1;
	public final static int SYMMETRIC_SWAP = 2;

	// All the getters and setters below are used in the gui.RandomizeableRankedData class. 
	// This is used to set the controls to the settings stored here (get), and set the controls
	// here from the settings (set)
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
	
	public static int getSumOfSymmetricWeightings()
	{
		int sum = 0;
		for (int i : symmetricWeightings)
		{
			sum += i;
		}
		
		return sum;
	}

	// This is the method that actually does the swapping of the different types. 
	public RankedData nextRankedData()
	{

		if (swapTechnique != SYMMETRIC_SWAP)
		{
			for (int i = 0; i < numberOfSwaps; i++)
			{
				int swapProb = (int) (Math.random() * 101);

				if (swapProb <= probabilityOfSwap)
				{
					// A random swap, or standard swap, randomly swaps two numbers
					// in the ranking
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
					
					// The distance swap randomly selects one element and switches it with the next elemnt.
					// If the last element is chosen, it is made negative. The randomDistanceSwap method
					// occurs in the parent RankedData class. 
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
			// The symmetric swap takes the original ranking and creates 20 chilren elements. 
			// 4 of the rankings are two away from the original rankings.
			// 8 of the rankings are one away from the original ranking, and there are 4 sets of 2.
			// 8 of the rankings are the original ranking itself. 
			symmetricSwapper(currentIndex);
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

	// This method changes the data used for swapping around. 
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

	// Not sure why this is here, probably not needed, but I don't want to change
	// so close to the final deadline. I just wanted to add some comments...
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

	// explained in the nextRankedData method. 
	public void symmetricSwapper(int index)
	{
		
		int sum = 0;
		int count = -1;
		while (sum <= index)
		{
			count++;
			sum+=symmetricWeightings[count]*numberOfRepeats;
		}
		if (count == 0)
		{
			randomDistanceSwap(0);
			randomDistanceSwap(2);
		}
		else if (count == 1)
		{
			randomDistanceSwap(0);
		}
		else if (count == 2)
		{
			randomDistanceSwap(0);
			randomDistanceSwap(6);
		}
		else if (count == 3)
		{
			randomDistanceSwap(2);
		}
		else if (count == 5)
		{
			randomDistanceSwap(6);
		}
		else if (count == 6)
		{
			randomDistanceSwap(2);
			randomDistanceSwap(4);
		}
		else if (count == 7)
		{
			randomDistanceSwap(4);
		}
		else if (count == 8)
		{
			randomDistanceSwap(4);
			randomDistanceSwap(6);
		}
	}

	// This method is called when the data is done being generated. This is called
	// by the gui component. 
	public void doneWithData()
	{
		currentIndex = 0;
	}

}
