package selectionListeners;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Text;
@Deprecated
public class SelectAllBehavior implements SelectionListener
{
	// This needs fixing since we switched to StyledText
	private Text text;
	public SelectAllBehavior(Text text)
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
		text.selectAll();
		
	}

}
