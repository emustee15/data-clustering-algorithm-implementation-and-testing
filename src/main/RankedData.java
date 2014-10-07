package main;
import java.util.ArrayList;

public class RankedData
{

	private ArrayList<Integer> data;
	private int[] originalData;

	public RankedData(int[] data)
	{
		this.data = new ArrayList<Integer>();
		for (int i : data)
		{
			this.data.add(i);
		}
		originalData = data.clone();
	}

	public int get(int index)
	{
		return data.get(index);
	}
	
	public boolean isPositive(int number)
	{
		return (data.get(getPosition(number)) > 0);
	}
	
	public boolean isIndexPositive(int index)
	{
		return (data.get(index) > 0);
	}
	
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
	
	public void negate(int index)
	{
		data.set(index, -data.get(index));
	}
	
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
	
	public void addToList(int i)
	{
		data.add(i);
	}
	
	public int getSize()
	{
		return data.size();
	}
	
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
	
	
	public ArrayList<Integer> getListElements()
	{
		return data;
	}
	
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
	
	public void saveCurrentDataAsDefault()
	{
		originalData = new int[data.size()];
		
		for (int i = 0; i < data.size(); i++)
		{
			originalData[i] = data.get(i);
		}
	}
	
	public RankedData clone() 
	{
		int[] newData = new int[originalData.length];
		
		for (int i=0; i<originalData.length; i++)
		{
			newData[i] = originalData[i];
		}
		
		return new RankedData(newData);
	}
	
	public void randomize()
	{
		
		for (int i = 0; i < 100;i++)
		{
			int positionA = (int) (Math.random()*data.size());
			int positionB = (int) (Math.random()*data.size());
		
			swapPositions(positionA,positionB);	
		}
		
		saveCurrentDataAsDefault();
		
		
	}
	
	@Override
	public String toString()
	{
		String string = "";
		
		string += "{";
		for (int i = 0; i < data.size(); i++)
		{
			string += data.get(i);
			string += (i != data.size()-1 ? ", " : "");
		}
		string += "}";
		return string;
	}
	
}
