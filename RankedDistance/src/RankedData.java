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
	

	
	public int getPosition(int number)
	{
		for (int i = 0; i < data.length; i++)
		{
			if (data[i] == number)
			{
				return i;
			}
		}
		
		return -1;
	}
	
	public void swapPositions(int a, int b)
	{
		if (a == b)
		{
			return;
		}

		int temp[] = data.clone();
		data[a] = data[b];
		for (int i = a+1; i <= b; i++)
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
