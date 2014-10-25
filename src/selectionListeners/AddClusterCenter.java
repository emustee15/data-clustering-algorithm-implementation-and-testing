package selectionListeners;

import main.FileType;
import main.RandomizableRankedData;
import main.RankedData;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;

import gui.ErrorDialog;
import gui.RandomDataGenerator;

public class AddClusterCenter implements SelectionListener
{
	static String[] appropriateDelimeters = new String[] { ", ", " ", ",", " ," };
	
	protected List list;
	protected Text text;
	protected final static int RANDOMIZED_RANKED_DATA = 0;
	protected final static int RANKED_DATA = 1;
	
	public AddClusterCenter(List list, Text text)
	{
		this.list = list;
		this.text = text;
	}
	@Override
	public void widgetDefaultSelected(SelectionEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void widgetSelected(SelectionEvent arg0)
	{
		try
		{
			RandomizableRankedData data = (RandomizableRankedData)parseLineOfRankedDataFile(text.getText(),RANDOMIZED_RANKED_DATA);
			list.add(data.toString());
			RandomDataGenerator.getInstance().addClusterCenter(data);
			list.setSelection(list.getItemCount()-1);
			RandomDataGenerator.getInstance().setCurrentSelection(list.getItemCount()-1);
		}
		catch (NumberFormatException nfEx)
		{
			ErrorDialog dialog = new ErrorDialog(RandomDataGenerator.getInstance(), RandomDataGenerator.getInstance().getStyle());
			dialog.open("Bad Formatting.");
		}
	}
	
	
	public RankedData parseLineOfRankedDataFile(String line, int type)
	{
		RankedData rankedData = null;
		
		if (type == RANDOMIZED_RANKED_DATA)
			rankedData = new RandomizableRankedData(new int[] {});
		else if (type == RANKED_DATA)
			rankedData =new RankedData(new int[] {});
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

}
