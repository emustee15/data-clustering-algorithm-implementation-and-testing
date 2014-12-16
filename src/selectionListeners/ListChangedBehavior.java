package selectionListeners;


import gui.RandomDataGenerator;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;

public class ListChangedBehavior implements SelectionListener
{
	// This class is called upon when the list changes in the random data generator. 
	private List list;
	private Text text;
	public ListChangedBehavior(List list, Text text)
	{
		this.list = list;
		this.text = text;
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void widgetSelected(SelectionEvent arg0)
	{
		int index = list.getSelectionIndex();
		
		if (index >= 0 && index < list.getItemCount())
		{
			RandomDataGenerator.getInstance().setCurrentSelection(index);
			String textString = list.getItem(index).replace("{", "").replace("}", "");
			text.setText(textString);
		}
		
	}




}
