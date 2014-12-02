package selectionListeners;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.List;

public class RemoveDescription implements SelectionListener {

	private List list;
	
	
	public RemoveDescription(List list)
	{
		this.list = list;
	}
	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void widgetSelected(SelectionEvent arg0) {
		int index = list.getSelectionIndex();
		if (index >= 0 && index < list.getItemCount())
		{
			list.remove(index);
			if (index < list.getItemCount())
			{
				list.setSelection(index);
			}
			else if (index > 0)
			{
				list.setSelection(index-1);
			}
		}
		
	}

}
