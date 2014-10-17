package selectionListeners;

import java.util.ArrayList;

import gui.SuperStyledText;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Text;

// This behavior zooms out on the styled text boxes

public class ZoomOutBehavior implements SelectionListener
{
	private ArrayList<SuperStyledText> textToZoom;
	private Display display;
	private MenuItem zoomOut, zoomIn;

	private final static int MIN_SIZE = 11;
	

	public ZoomOutBehavior(ArrayList<SuperStyledText> text, Display display, MenuItem zoomOut, MenuItem zoomIn)
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

		if (ZoomInBehavior.size > MIN_SIZE)
		{			
			for (SuperStyledText sst : textToZoom)
			{
				sst.makeSmaller();
			}
		}
		
		ZoomInBehavior.size--;
		
		if (ZoomInBehavior.size == MIN_SIZE)
		{
			zoomOut.setEnabled(false);
		}

		zoomIn.setEnabled(true);


	}
}
