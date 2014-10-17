package selectionListeners;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Text;

@Deprecated
public class CopyBehavior implements SelectionListener
{

	// This class needs to be fixed because we now use StyledTexts
	
	private Text text;
	public CopyBehavior(Text text)
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
		text.copy();
		
	}

}
