package main;

import exception.DuplicateIntegerException;
import exception.EmptyPiVectorException;
import exception.ZeroInFileException;
import gui.ErrorDialog;
import gui.MainGUI;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class FileLoader
{

	boolean testingMode = false; //Used for tesing purposes only, so we don't need errorDialog and MainGui
	ArrayList<RankedData> partialRankings;
	static String[] appropriateDelimeters = new String[] { ", ", " ", ",", " ," };
	ArrayList<String> descriptions;
	ErrorDialog errorDialog;
	private Settings settings;
	
	public FileLoader()
	{
			errorDialog = new ErrorDialog(MainGUI.getInstance(), MainGUI
					.getInstance().getStyle());	
	}
	
	//This constructor is only used in JUnit testing
	public FileLoader(boolean testingMode)
	{
		this.testingMode = testingMode;
		
		if (!testingMode) 
		{
			errorDialog = new ErrorDialog(MainGUI.getInstance(), MainGUI
					.getInstance().getStyle());
		}
	}

	/*
	 * This class takes in a file and turns it into a RankedData ArrayList or a
	 * description ArrayList. Descriptions (not yet implemented) will describe
	 * the data instead of numerically. The loaded ranked data is currently used
	 * as the piVector.
	 */

	// This method loads a file. Valid file types are FileType.RankedData and
	// FileType.NounList.
	public void loadFile(String filePath, FileType type)
	{
		if (type.equals(FileType.RankedData))
		{
			partialRankings = new ArrayList<RankedData>();
		}
		else if (type.equals(FileType.WordList))
		{
			descriptions = new ArrayList<String>();
		}

		BufferedReader reader = null;
		ObjectInputStream objectInputStream = null;

		int count = 0;
		try
		{

			if (type.equals(FileType.RankedData))
			{
				reader = new BufferedReader(new FileReader(filePath));
				String line;
				while ((line = reader.readLine()) != null)
				{
					partialRankings.add(parseLineOfRankedDataFile(line));
					count++;
				}

				if (count == 0)
				{
					throw new EmptyPiVectorException();
				}
			}
			else if (type.equals(FileType.WordList))
			{
				reader = new BufferedReader(new FileReader(filePath));
				String line;
				while ((line = reader.readLine()) != null)
				{
					String[] newNouns = parseDescriptionList(line);
					for (String s : newNouns)
					{
						descriptions.add(s);
					}
				}
			}
			else if (type.equals(FileType.Settings))
			{
				FileInputStream input = new FileInputStream(filePath);
				objectInputStream = new ObjectInputStream(input);
				settings = (Settings) objectInputStream.readObject();
			}
		}
		catch (NumberFormatException nfEx)
		{
			if (!testingMode)
			{
				errorDialog
					.open("File not formatted correctly. Please read the Help menu for formatting guidelines.");
			}
			
		}
		catch (ZeroInFileException zifEx)
		{
			if (!testingMode)
			{
				errorDialog
					.open("File contains ranked data with a 0 in it. Please use only nonzero integers.");
			}
			
		}
		catch (DuplicateIntegerException diEx)
		{
			if (!testingMode)
			{
				errorDialog
					.open("File contains a ranked data set with duplicate values. Please read the Help menu for formatting guidelines.");
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
		catch (EmptyPiVectorException epvEx)
		{
			if (!testingMode)
			{
				errorDialog
					.open("The file must contain one π vector. Please read the Help menu for formatting guidelines.");
			}
			
		}
		catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (reader != null)
				{
					reader.close();
				}
				if (objectInputStream != null)
				{
					objectInputStream.close();
				}
			}
			catch (IOException ioEx)
			{
				// Wow really bad luck to get here :/
			}
		}

	}

	// This method gets the loaded partial rankings.
	public ArrayList<RankedData> getPartialRankings()
	{
		return partialRankings;
	}

	// This method parses one line of a ranked data file.
	public static RankedData parseLineOfRankedDataFile(String line)
			throws EmptyPiVectorException, ZeroInFileException, DuplicateIntegerException
	{
		RankedData rankedData = new RankedData(new int[] {});
		String[] data = seperateByAllowedDelimeters(line, FileType.RankedData);
		for (String s : data)
		{
			Integer i = Integer.parseInt(s);
			if (i != null)
			{
				if (i == 0)
				{
					throw new ZeroInFileException();
				}
				else 
				{
					rankedData.addToList(i);
				}
				
			}
		}
		/* If we get to this point, this means that rankedData contains only
		* nonzero integers. Now just search through the rankedData to see if
		* there are any duplicate numbers.
		*/
		for (int i=0; i<rankedData.getListElements().size()-1; i++)
		{
			for (int j=i+1; j<rankedData.getListElements().size(); j++)
			{
				if (Math.abs(rankedData.getListElements().get(i)) == Math.abs(rankedData.getListElements().get(j)))
				{
					throw new DuplicateIntegerException();
				}
			}
		}

		rankedData.saveCurrentDataAsDefault();
		return rankedData;
	}

	// This will return a single list of descriptions.
	public String[] parseDescriptionList(String line)
	{
		return seperateByAllowedDelimeters(line, FileType.WordList);
	}

	// This method separates the values by the allowed delimiters defined in the file.
	// First, we attempt to separate the objects by assuming the user used consistent
	// spacing in the file. If that does not work, then we completely remove all spacing
	// and try again. 
	private static String[] seperateByAllowedDelimeters(String line, FileType type)
	{
		String[] data = null;

		int count = 0;
		boolean triedRemovingSpaces = false;
		while (data == null)
		{
			if (count < appropriateDelimeters.length)
			{
				data = line.split(appropriateDelimeters[count]);
				if (type.equals(FileType.RankedData))
				{
					try
					{

						for (String d : data)
						{
							Integer.parseInt(d);
						}
					}
					catch (NumberFormatException nfEx)
					{
						data = null;
					}
				}
			}
			else
			{
				if (triedRemovingSpaces)
				{
					throw new NumberFormatException();
				}

				triedRemovingSpaces = true;
				line = line.replace(" ", "");
				count = 0;
			}
			count++;
		}
		return data;
	}

	public Settings getSettings()
	{
		return settings;
	}
	
	// This method returns the description for an element of some ranking. 
	public String getDescription(int index)
	{
		index = Math.abs(index);

		if (descriptions == null)
		{
			return "undefined";
		}

		if (index > descriptions.size())
		{
			return "undefined";
		}

		return descriptions.get(index);
	}
	
}
