import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileLoader
{
	RankedData masterRanking;
	ArrayList<RankedData> partialRankings;
	
	public void loadFile(String filePath)
	{
		partialRankings = new ArrayList<RankedData>();
		BufferedReader reader = null;
		try
		{
			reader = new BufferedReader(new FileReader(filePath));
			String line = reader.readLine();
			masterRanking = parseLineOfFile(line);
			while ((line = reader.readLine()) != null)
			{
				partialRankings.add(parseLineOfFile(line));
			}
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException ioEx)
		{
			
		}
		finally
		{
			try
			{
				reader.close();
			}
			catch (NullPointerException npEx)
			{
				
			}
			catch (IOException ioEx)
			{
				// Wow really bad luck to get here :/
			}
		}
		
	}
	
	
	public RankedData getMasterRanking()
	{
		return masterRanking;
	}
	
	public ArrayList<RankedData> getPartialRankings()
	{
		return partialRankings;
	}
	
	
	public RankedData parseLineOfFile(String line)
	{
		RankedData rankedData = new RankedData(new int[] {});
		String[] data = line.split(", ");
		for (String s : data)
		{
			Integer i = Integer.parseInt(s);
			if (i != null)
			{
			rankedData.addToList(i);
			}
		}
		
		rankedData.saveCurrentDataAsDefault();
		return rankedData;
	}
}
