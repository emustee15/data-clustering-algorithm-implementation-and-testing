package selectionListeners;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

public class OpenFileBehavior implements SelectionListener
{
	private Shell shell;
	private String selectedFile;
	
	public OpenFileBehavior(Shell shell)
	{
		this.shell = shell;
	}
	
	
	@Override
	public void widgetDefaultSelected(SelectionEvent arg0)
	{


		
	}

	@Override
	public void widgetSelected(SelectionEvent arg0)
	{
		FileDialog dialog = new FileDialog(shell, SWT.OPEN);
		dialog.setText("Open");
		dialog.setFilterPath("C:/");
		String[] filterExt = {"*.txt"};
		dialog.setFilterExtensions(filterExt);
		selectedFile = dialog.open();
		
	}
	
	public String getSelectedFile()
	{
		return selectedFile;
	}



}
