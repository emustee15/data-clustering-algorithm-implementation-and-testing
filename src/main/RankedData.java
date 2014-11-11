package main;
import gui.MainGUI;

import java.io.Serializable;
import java.util.ArrayList;

public class RankedData implements Serializable
{
	// This class represents a single ranking. A ranking is a set of relationships between objects such that
	// one object is higher than another (e.g. {1,2,3,4,5} where order matters.) 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected ArrayList<Integer> data;
	private int[] originalData;


	// All we need is an integer array. 
	public RankedData(int[] data)
	{
		this.data = new ArrayList<Integer>();
		for (int i : data)
		{
			this.data.add(i);
		}
		originalData = data.clone();
	}

	// This gets an index of the ranked data.
	public int get(int index)
	{
		return data.get(index);
	}

	// This returns whether a certain number in a ranked data is positive.
	// e.g. in {4,-1,2,3,5}, the number 1 is negative. 
	public boolean isPositive(int number)
	{
		return (data.get(getPosition(number)) > 0);
	}

	// This returns whether a certain index is negative.
	// e.g. in {4,-1,2,3,5}, the 2nd number is negative.
	public boolean isIndexPositive(int index)
	{
		return (data.get(index) > 0);
	}

	// This returns the position (index) of a number.
	public int getPosition(int number)
	{
		for (int i = 0; i < data.size(); i++)
		{
			if (data.get(i) == number || data.get(i) == -number)
			{
				return i;
			}
		}

		return -1;
	}

	// This method bubbles the number at indexB up to indexA. It does this quickly
	// by simply placing the number at indexB in indexA's location, and simply cloning
	// the rest of the array into the old position.
	public void push(int indexA, int indexB)
	{
		if (indexA == indexB)
		{
			return;
		}

		@SuppressWarnings("unchecked")
		ArrayList<Integer> temp = (ArrayList<Integer>) data.clone();
		data.set(indexA,data.get(indexB));
		for (int i = indexA+1; i <= indexB; i++)
		{
			data.set(i,temp.get(i-1));
		}
	}

	// This method swaps the positions of indexA and indexB
	public void swapPositions(int indexA, int indexB)
	{
		if (indexA == indexB)
		{
			return;
		}

		int temp = data.get(indexA);
		data.set(indexA, data.get(indexB));
		data.set(indexB, temp);
	}

	// This method negates a number at some index.
	public void negate(int index)
	{
		data.set(index, -data.get(index));
	}

	// This method restores the array back to its default configuration.
	// This is done to partial rankings after the distance to a complete
	// ranking has been measured.
	public void reset()
	{
		for (int i = data.size()-1; i >= 0; i--)
		{
			data.remove(i);
		}
		for (int index = 0; index < originalData.length; index++)
		{
			data.add(originalData[index]);
		}
	}

	// This method adds an element to the list. It is necessary to remember to
	// call saveCurrentDataAsDefault() if we want to remember the addition for
	// more than one comparison. 
	public void addToList(int i)
	{
		data.add(i);
	}

	// This method returns how many elements are in the ranking. 
	public int getSize()
	{
		return data.size();
	}

	/* This method makes a partial list compatible with a complete list. This means the
	/* lists have the same elements in any order. We assume that an incomplete list is as 
	/* close to the complete list as possible, so we add the missing elements in the order
	/* they appear on the complete list. Below, Partial_i is the ranking before it is made
	 * compatible with the complete ranking, and Partial_f is the ranking after it is made
	 * compatible with the complete ranking.
	 *
	 * Partial_i	Partial_f	Complete
	 * 1,2			1,2,3,5,4	3,2,5,4,1
	 */
	public void makeCompatableWithList(RankedData list)
	{
		for (int i = 0; i < list.getSize(); i++)
		{
			if (!data.contains(list.get(i)) && !data.contains(-list.get(i)))
			{
				data.add(list.get(i));
			}
		}
	}

	// This method returns whether two lists are compatible with each other. Two lists are compatible
	// if they have the same elements.
	public boolean isCompatableWithList(RankedData list)
	{
		for (int i = 0; i < list.getSize(); i++)
		{
			if (!data.contains(list.get(i)) && !data.contains(-(list.get(i))))
			{
				return false;
			}
		}

		return true;
	}

	// This method returns the elements.
	public ArrayList<Integer> getListElements()
	{
		return data;
	}

	// This method displays the list in the console. 
	public void print()
	{
		System.out.print("{");
		for (int i = 0; i < data.size(); i++)
		{
			if (i != data.size()-1)
			{
				System.out.print(data.get(i) + ", ");
			}
			else
			{
				System.out.print(data.get(i));
			}
		}
		System.out.print("}\n");
	}

	// This method saves the current configuration of the data as the default, so the next time it is ranked,
	// it will reset back to the position after this is called.
	public void saveCurrentDataAsDefault()
	{
		originalData = new int[data.size()];

		for (int i = 0; i < data.size(); i++)
		{
			originalData[i] = data.get(i);
		}
	}

	// This method makes a clone of the ranked data.
	public RankedData clone() 
	{
		int[] newData = new int[originalData.length];

		for (int i=0; i<originalData.length; i++)
		{
			newData[i] = originalData[i];
		}

		return new RankedData(newData);
	}

	// This method randomizes the ranked data.
	public void randomize()
	{

		boolean used[] = new boolean[data.size()];
		int count = 0;

		while (count < data.size())
		{
			int newValue = (int)(Math.random()*data.size());
			if (!used[newValue])
			{
				used[newValue] = true;
				newValue++;
				newValue = (Math.random() < .5 ? newValue*-1:newValue);
				data.set(count, newValue);
				count++;
			}
		}

		saveCurrentDataAsDefault();


	}


	// This method returns a string representation of the ranked data. 

	@Override
	public String toString()
	{
		return toString(false);
	}
	public String toString(boolean descriptions)
	{
		String string = "";
		string += "{";

		for (int i = 0; i < data.size(); i++)
		{
			if (descriptions)
			{
				string+=MainGUI.getDesciptList(data.get(i));
				string += (i != data.size()-1 ? ", " : "");
			}
			else
			{
				string +=data.get(i);
				string += (i != data.size()-1 ? ", " : "");
			}

		}
		string += "}";
		return string;
	}

	// This method gets the largest value in the ranking. 
	public int largestValue()
	{
		int largest = 0;
		for (int i: data)
		{
			if (Math.abs(i) > largest)
			{
				largest = Math.abs(i);
			}
		}

		return largest;
	}

	public void randomDistanceSwap(int index)
	{
		if (index == data.size() - 1)
		{
			negate(index);
		}
		else
		{
			swapPositions(index, index+1);
		}
	}

}
