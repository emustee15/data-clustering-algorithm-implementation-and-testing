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
	
	public void swapPositions(int indexA, int indexB)
	{
		if (indexA == indexB)
		{
			return;
		}

		ArrayList<Integer> temp = (ArrayList<Integer>) data.clone();
		data.set(indexA,data.get(indexB));
		for (int i = indexA+1; i <= indexB; i++)
		{
			data.set(i,temp.get(i-1));
		}
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
		System.out.print("}");
	}
	
	public void saveCurrentDataAsDefault()
	{
		originalData = new int[data.size()];
		
		for (int i = 0; i < data.size(); i++)
		{
			originalData[i] = data.get(i);
		}
	}
}
