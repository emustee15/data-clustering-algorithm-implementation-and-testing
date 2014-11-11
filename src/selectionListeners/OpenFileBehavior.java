package selectionListeners;

import gui.MainGUI;
import main.FileLoader;
import main.FileType;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

// This class is used when the user opens a file using open from the file menu. 

public class OpenFileBehavior implements SelectionListener
{
	private Shell shell;
	private String selectedFile;
	private FileType type;

	public OpenFileBehavior(Shell shell, FileType type)
	{
		this.shell = shell;
		this.type = type;
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent arg0)
	{

	}

	@Override
	public void widgetSelected(SelectionEvent arg0)
	{
		if (type.equals(type.equals(FileType.RankedData)))
		{
			FileDialog dialog = new FileDialog(shell, SWT.OPEN);
			dialog.setText("Open");
			dialog.setFilterPath("C:/");
			String[] filterExt = { "*.txt", "*.csv" };
			dialog.setFilterExtensions(filterExt);
			dialog.setFilterNames(new String[] { "*.txt (Text Files)", "*.csv (Comma Separated Value Files)" });
			selectedFile = dialog.open();

			if (selectedFile != null && shell instanceof MainGUI)
			{
				((MainGUI) (shell)).openPiVector(selectedFile);
			}
		}
		else if (type.equals(FileType.Settings))
		{
			FileDialog dialog = new FileDialog(shell, SWT.OPEN);
			dialog.setText("Open");
			dialog.setFilterPath("C:/");
			String[] filterExt = { "*.rnkr" };
			dialog.setFilterExtensions(filterExt);
			dialog.setFilterNames(new String[] { "*.rnkr (Ranked Data Settings File)"});
			selectedFile = dialog.open();
			
			if (selectedFile != null && shell instanceof MainGUI)
			{
				FileLoader loader = new FileLoader();
				loader.loadFile(selectedFile, FileType.Settings);
				MainGUI.getInstance().openSettings(loader.getSettings());
			}
		}

	}

	public String getSelectedFile()
	{
		return selectedFile;
	}

}
