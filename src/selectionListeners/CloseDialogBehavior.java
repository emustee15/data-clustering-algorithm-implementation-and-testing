package selectionListeners;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Shell;

/*
 * This class is used when closing a dialog.
 */
public class CloseDialogBehavior implements SelectionListener 
{	
	private Shell shell;

	public CloseDialogBehavior(Shell shell) 
	{
		this.shell = shell;
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) 
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void widgetSelected(SelectionEvent arg0)
	{
		shell.dispose();

	}

}
