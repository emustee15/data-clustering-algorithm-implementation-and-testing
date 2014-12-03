package selectionListeners;

import exception.InvalidCharacterException;
import gui.ErrorDialog;
import gui.MainGUI;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;

public class AddDescriptions implements SelectionListener {

	protected List list;
	protected Text descriptions;

	public AddDescriptions(List list, Text descriptions)
	{
		this.list = list;
		this.descriptions = descriptions;

	}
	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {


	}

	@Override
	public void widgetSelected(SelectionEvent arg0) {
		try
		{
			if (descriptions.getText().contains("~") || descriptions.getText().contains("\\"))
			{
				throw new InvalidCharacterException();
			}
			String data = (descriptions.getText());
			list.add(data);
			list.setSelection(list.getItemCount()-1);
			descriptions.setText("");
			MainGUI.modifyStateFlag();
		}
		catch (InvalidCharacterException icEx)
		{
			ErrorDialog dialog = new ErrorDialog(MainGUI.getInstance());
			dialog.open("Bad Formatting.\nPlease do not use the '\\' or '~' character.");
		}
	}
	


}
