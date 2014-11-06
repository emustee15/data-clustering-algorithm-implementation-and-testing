package selectionListeners;

import gui.ErrorDialog;
import gui.RandomDataGenerator;
import main.RandomizableRankedData;

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
			String data = (descriptions.getText());
			list.add(data);
			list.setSelection(list.getItemCount()-1);
		}
		catch (NumberFormatException nfEx)
		{
			ErrorDialog dialog = new ErrorDialog(RandomDataGenerator.getInstance(), RandomDataGenerator.getInstance().getStyle());
			dialog.open("Bad Formatting.");
		}
	}
	


}
