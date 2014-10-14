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

	public void loadFile(String filePath)
	{
		partialRankings = new ArrayList<RankedData>();
		BufferedReader reader = null;
		
		int count = 0;
		try
		{
			reader = new BufferedReader(new FileReader(filePath));
			String line;
			while ((line = reader.readLine()) != null)
			{
				partialRankings.add(parseLineOfFile(line));
				count++;
			}
			
			if (count == 0)
			{
				throw new EmptyPiVectorException();
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
			errorDialog.open("The file must contain one π vector. Please read the Help menu for formatting guidelines.");
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

	public RankedData parseLineOfFile(String line) throws EmptyPiVectorException
	{
		RankedData rankedData = new RankedData(new int[] {});

		String[] data = null;

		int count = 0;
		while (data == null)
		{
			if (count < appropriateDelimeters.length)
			{
				data = line.split(appropriateDelimeters[count]);
				try
				{
					Integer.parseInt(data[0]);
				}
				catch (NumberFormatException nfEx)
				{
					data = null;
				}
			}
			else
			{
				throw new NumberFormatException();
			}
			count++;
		}
		
		
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
