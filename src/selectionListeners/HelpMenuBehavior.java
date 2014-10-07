package selectionListeners;

import java.io.File;
import java.io.IOException;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

public class HelpMenuBehavior implements SelectionListener{

	
	public HelpMenuBehavior()
	{
	
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void widgetSelected(SelectionEvent arg0) {
		// TODO Auto-generated method stub
		File file = new File("Data Clustering Algorithm help.chm");
		try
		{
		 Runtime.getRuntime().exec("HH.EXE ms-its:" + file.getAbsolutePath() + "::/TOPIC_ID.html");
		} catch (IOException e1)
		{
		 e1.printStackTrace();
		}
	}
}
