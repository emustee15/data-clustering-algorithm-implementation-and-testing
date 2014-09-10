public class RankedData
{

	private int[] data;
	private int[] originalData;
	public int length;

	public RankedData(int[] data)
	{
		this.data = data;
		length = data.length;
		originalData = data.clone();
	}

	public int get(int index)
	{
		return data[index];
	}
	
	public boolean isPositive(int number)
	{
		return (data[getPosition(number)] > 0);
	}
	

	
	public int getPosition(int number)
	{
		for (int i = 0; i < data.length; i++)
		{
			if (data[i] == number || data[i] == -number)
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

		int temp[] = data.clone();
		data[indexA] = data[indexB];
		for (int i = indexA+1; i <= indexB; i++)
		{
			data[i] = temp[i-1];
		}
	}
	
	public void reset()
	{
		for (int index = 0; index < originalData.length; index++)
		{
			data[index] = originalData[index];
		}
	}
}
