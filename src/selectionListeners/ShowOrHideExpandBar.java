package selectionListeners;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

// This class hides the expand bar on the left side of the screen.
public class ShowOrHideExpandBar implements SelectionListener
{
	private ExpandBar bar;
	private Shell shell;
	private MenuItem checkbox;
	
	private int oldWidth;
	
	public ShowOrHideExpandBar(ExpandBar bar, Shell shell, MenuItem checkbox)
	{
		this.bar = bar;
		this.shell = shell;
		this.checkbox = checkbox;
	}
	@Override
	public void widgetDefaultSelected(SelectionEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void widgetSelected(SelectionEvent arg0)
	{
		GridData barGridData = ((GridData)bar.getLayoutData());
		if (checkbox.getSelection())
		{
			barGridData.widthHint = oldWidth;
		}
		else
		{

			oldWidth = barGridData.widthHint;
			barGridData.widthHint = -20;
		}
		
		shell.layout();
	}

}
