package selectionListeners;

import gui.MainGUI;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

public class CopyBehavior implements SelectionListener
{

	// This class is called when the Edit>Copy is selected.
	@Override
	public void widgetDefaultSelected(SelectionEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void widgetSelected(SelectionEvent arg0)
	{
		MainGUI.getInstance().getActiveTab().copy();
		
	}

}
