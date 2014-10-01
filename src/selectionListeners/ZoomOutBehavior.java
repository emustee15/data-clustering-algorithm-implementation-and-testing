package selectionListeners;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Text;

public class ZoomOutBehavior implements SelectionListener
{
	private Text textToZoom;
	private Display display;
	private MenuItem zoomOut, zoomIn;
	
	private final static int MIN_SIZE = 4;
	public ZoomOutBehavior(Text text, Display display, MenuItem zoomOut, MenuItem zoomIn)
	{
		textToZoom = text;
		this.display = display;
		this.zoomOut = zoomOut;
		this.zoomIn = zoomIn;
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
			
			data[i].setHeight((data[i].getHeight() - 2 <= MIN_SIZE ? MIN_SIZE : data[i].getHeight() - 2));
		}
		
		if (data[0].getHeight() == MIN_SIZE)
		{
			zoomOut.setEnabled(false);
		}
		
		zoomIn.setEnabled(true);
		
		Font biggerFont = new Font(display, data);
		textToZoom.setFont(biggerFont);
		
	}
}
