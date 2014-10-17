package selectionListeners;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Text;
@Deprecated
public class PasteBehavior implements SelectionListener
{
	// You cannot paste text into a read-only field, which all our fields are. 
	private Text text;
	public PasteBehavior(Text text)
	{
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
		text.paste();
		
	}

}
