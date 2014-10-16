package main;

import exception.EmptyPiVectorException;
import gui.ErrorDialog;
import gui.MainGUI;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileLoader
{

	ArrayList<RankedData> partialRankings;
	ErrorDialog errorDialog = new ErrorDialog(MainGUI.getInstance(), MainGUI
			.getInstance().getStyle());
	String[] appropriateDelimeters = new String[] { ", ", " ", ",", " ," };
	ArrayList<String> descriptions;

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
		else if (type.equals(FileType.NounList))
		{
			descriptions = new ArrayList<String>();
		}

		BufferedReader reader = null;

		int count = 0;
		try
		{
			reader = new BufferedReader(new FileReader(filePath));
			String line;
			if (type.equals(FileType.RankedData))
			{
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
			else if (type.equals(FileType.NounList))
			{
				while ((line = reader.readLine()) != null)
				{
					String[] newNouns = parseDescriptionList(line);
					for (String s : newNouns)
					{
						descriptions.add(s);
					}
				}
			}
		}
		catch (NumberFormatException nfEx)
		{
			errorDialog
					.open("File not formatted correctly. Please read the Help menu for formatting guidelines.");
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
			errorDialog
					.open("The file must contain one π vector. Please read the Help menu for formatting guidelines.");
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

	// This method gets the loaded partial rankings.
	public ArrayList<RankedData> getPartialRankings()
	{
		return partialRankings;
	}

	// This method parses one line of a ranked data file.
	public RankedData parseLineOfRankedDataFile(String line)
			throws EmptyPiVectorException
	{
		RankedData rankedData = new RankedData(new int[] {});
		String[] data = seperateByAllowedDelimeters(line, FileType.RankedData);
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

	// This will return a single list of descriptions.
	public String[] parseDescriptionList(String line)
	{
		return seperateByAllowedDelimeters(line, FileType.NounList);
	}

	// This method separates the values by the allowed delimiters defined in the file.
	// First, we attempt to separate the objects by assuming the user used consistent
	// spacing in the file. If that does not work, then we completely remove all spacing
	// and try again. 
	private String[] seperateByAllowedDelimeters(String line, FileType type)
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
