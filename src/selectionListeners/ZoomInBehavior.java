package selectionListeners;

import java.util.ArrayList;

import gui.SuperStyledText;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.MenuItem;

// This behavior zooms in on the styledtext boxes.
public class ZoomInBehavior implements SelectionListener
{
	private ArrayList<SuperStyledText> textToZoom;
	private MenuItem zoomOut, zoomIn;
	public static int size = 14;

	private final static int MAX_SIZE = 20;
	

	public ZoomInBehavior(ArrayList<SuperStyledText> text, MenuItem zoomOut, MenuItem zoomIn)
	{
		textToZoom = text;
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

		if (size < MAX_SIZE)
		{			
			for (SuperStyledText sst : textToZoom)
			{
				sst.makeBigger();
			}
		}
		
		size++;
		
		if (size == MAX_SIZE)
		{
			zoomIn.setEnabled(false);
		}

		zoomOut.setEnabled(true);


	}
}
