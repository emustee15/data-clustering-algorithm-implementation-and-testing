package selectionListeners;

import gui.ErrorDialog;
import gui.MainGUI;
import gui.RandomDataGenerator;
import main.RankedData;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;

public class ChangeClusterCenterBehavior extends AddClusterCenter
{

	public ChangeClusterCenterBehavior(List list, Text text)
	{
		super(list, text);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void widgetSelected(SelectionEvent arg0)
	{
		try
		{
			if (list.getSelectionIndex() >= 0 && list.getSelectionIndex() < list.getItemCount())
			{
				RankedData data = parseLineOfRankedDataFile(text.getText(), RANKED_DATA);
				list.setItem(list.getSelectionIndex(), data.toString());
				RandomDataGenerator.getInstance().modifyDataOfCurrentCluster(data);
				MainGUI.modifyStateFlag();
			}
		}
		catch (NumberFormatException nfEx)
		{
			ErrorDialog dialog = new ErrorDialog(RandomDataGenerator.getInstance());
			dialog.open("Bad Formatting.");
		}
	}

}
