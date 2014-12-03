package selectionListeners;

import org.eclipse.swt.widgets.Event;

import gui.MainGUI;
import main.FileType;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.MessageBox;

// This class is used when the program quits.

public class ExitBehavior implements SelectionListener
{

	private Event eventToCancel;

	@Override
	public void widgetDefaultSelected(SelectionEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	public void setEventToCancel(Event arg0)
	{
		this.eventToCancel = arg0;
	}

	@Override
	public void widgetSelected(SelectionEvent arg0)
	{
		if (MainGUI.getModifiedState())
		{
			MessageBox messageBox = new MessageBox(MainGUI.getInstance(), SWT.APPLICATION_MODAL | SWT.YES | SWT.NO | SWT.CANCEL | SWT.ICON_WARNING);
			messageBox.setMessage("Would you like to save before exiting?");
			messageBox.setText("Are you sure?");
			int result = messageBox.open();
			switch (result)
			{
			case SWT.NO:
				System.exit(0);
				break;
			case SWT.YES:
				SaveFileBehavior behavior = new SaveFileBehavior(FileType.Settings);
				behavior.widgetSelected(arg0);
				if (behavior.getSuccsess())
				{
					System.exit(0);
				}
				else
				{
					if (eventToCancel != null)
						eventToCancel.doit = false;
				}
				break;
			case SWT.CANCEL:
				if (eventToCancel != null)
					eventToCancel.doit = false;
				break;
			}
		}
	}

}
