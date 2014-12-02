package selectionListeners;

import gui.MainGUI;
import main.FileType;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.FileDialog;

public class SaveFileBehavior implements SelectionListener
{
	private FileType type;

	public SaveFileBehavior(FileType type)
	{
		this.type = type;
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void widgetSelected(SelectionEvent arg0)
	{
		String selectedFile = null;
		FileDialog dialog = new FileDialog(MainGUI.getInstance(), SWT.SAVE);
		dialog.setText("Save");
		dialog.setFilterPath("C:/");
		if (type.equals(FileType.Settings))
		{

			String[] filterExt = { "*.rnkr" };
			dialog.setFilterExtensions(filterExt);
			dialog.setFilterNames(new String[] { "*.rnkr (Ranked Data Settings File" });
			selectedFile = dialog.open();

			if (selectedFile != null)
			{
				MainGUI.getInstance().saveSettings(selectedFile);
			}
		}
		else if (type.equals(FileType.ExportedResults))
		{
			String[] filterExt = { "*.txt" };
			dialog.setFilterExtensions(filterExt);
			dialog.setFilterNames(new String[] { "*.txt (Text File)" });
			selectedFile = dialog.open();

			if (selectedFile != null)
			{
				MainGUI.getInstance().exportResults(selectedFile);
			}
		}

	}

}
