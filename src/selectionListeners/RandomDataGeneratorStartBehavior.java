package selectionListeners;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

public class RandomDataGeneratorStartBehavior implements SelectionListener
{

	// This class starts the random data generator.
	@Override
	public void widgetDefaultSelected(SelectionEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void widgetSelected(SelectionEvent arg0)
	{
		gui.RandomDataGenerator.startRandomDataGenerator();
		
	}

}
