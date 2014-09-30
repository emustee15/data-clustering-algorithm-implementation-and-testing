package selectionListeners;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;

public class ZoomInBehavior implements SelectionListener
{
	private Text textToZoom;
	private Display display;
	public ZoomInBehavior(Text text, Display display)
	{
		textToZoom = text;
		this.display = display;
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void widgetSelected(SelectionEvent arg0)
	{
		Font font = textToZoom.getFont();
		FontData[] data = font.getFontData();
		for (int i = 0; i < data.length; i++)
		{
			data[i].setHeight(24);
		}
		
		Font biggerFont = new Font(display, data);
		textToZoom.setFont(biggerFont);
		
	}
}
