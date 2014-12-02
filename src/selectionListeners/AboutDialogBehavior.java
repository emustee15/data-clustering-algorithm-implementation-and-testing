package selectionListeners;

import gui.AboutDialog;
import gui.MainGUI;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

public class AboutDialogBehavior implements SelectionListener
{

	@Override
	public void widgetDefaultSelected(SelectionEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void widgetSelected(SelectionEvent arg0)
	{
		AboutDialog dialog = new AboutDialog(MainGUI.getInstance());
		dialog.open();
		
	}

}
