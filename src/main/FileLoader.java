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
	ErrorDialog errorDialog = new ErrorDialog(MainGUI.getInstance(), MainGUI.getInstance().getStyle());
	String[] appropriateDelimeters = new String[] { ", ", " ", ",", " ," };
	ArrayList<String> nouns;

	public void loadFile(String filePath, FileType type)
	{
		if (type.equals(FileType.RankedData))
		{
			partialRankings = new ArrayList<RankedData>();
		}
		else if (type.equals(FileType.NounList))
		{
			nouns = new ArrayList<String>();
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
					String[] newNouns = parseNounList(line);
					for (String s : newNouns)
					{
						nouns.add(s);
					}
				}
			}
		}
		catch (NumberFormatException nfEx)
		{
			errorDialog.open("File not formatted correctly. Please read the Help menu for formatting guidelines.");
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

	public ArrayList<RankedData> getPartialRankings()
	{
		return partialRankings;
	}

	public RankedData parseLineOfRankedDataFile(String line) throws EmptyPiVectorException
	{
		RankedData rankedData = new RankedData(new int[] {});
		String[] data = seperateByAllowedDelimeters(line);
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

	public String[] parseNounList(String line)
	{
		return seperateByAllowedDelimeters(line);
	}

	private String[] seperateByAllowedDelimeters(String line)
	{
		String[] data = null;

		int count = 0;
		boolean triedRemovingSpaces = false;
		while (data == null)
		{
			if (count < appropriateDelimeters.length)
			{
				data = line.split(appropriateDelimeters[count]);
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

	public String getNoun(int index)
	{
		index = Math.abs(index);

		if (nouns == null)
		{
			return "undefined";
		}

		if (index > nouns.size())
		{
			return "undefined";
		}

		return nouns.get(index);
	}
}
