package selectionListeners;

import gui.RandomDataGenerator;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.List;

public class RemoveClusterCenter implements SelectionListener
{

	private List list;

	public RemoveClusterCenter(List list)
	{
		this.list = list;
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void widgetSelected(SelectionEvent arg0)
	{
		int index = list.getSelectionIndex();
		if (index >= 0 && index < list.getItemCount())
		{
			list.remove(index);
			RandomDataGenerator.getInstance().removeClusterCenter(index);
			if (index < list.getItemCount())
			{
				RandomDataGenerator.getInstance().setCurrentSelection(index);
				list.setSelection(index);
			}
			else if (index > 0)
			{
				RandomDataGenerator.getInstance().setCurrentSelection(index-1);
				list.setSelection(index-1);
			}
			else
			{
				RandomDataGenerator.getInstance().noSelection();
			}
		}

	}

}
