package GUI;
import org.eclipse.swt.*;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class StartScreen 
{
	public static void main(String[] args)
	{
		Display display = new Display();
		//Prevent window from being resizable
		final Shell shell = new Shell(display, SWT.CLOSE | SWT.TITLE | SWT.MIN );
		shell.setText("Ranked Data Cluster Analysis");
		
		RowLayout layout = new RowLayout();
		layout.type = SWT.VERTICAL;
		layout.center = true;	
		layout.marginWidth = 270;
		layout.marginTop = 190;
		layout.spacing = 25;
		shell.setLayout(layout);
		
		Label label = new Label(shell, SWT.BORDER);
		label.setText("Hello");
		label.setSize(100, 100);
		
		Button loadButton = new Button(shell, SWT.PUSH);
		loadButton.setText("Load File");
		loadButton.setLayoutData(new RowData(100, 50));
		Button helpButton = new Button(shell, SWT.PUSH);
		helpButton.setText("Help");
		helpButton.setLayoutData(new RowData(100, 50));
		
	    shell.pack();
	    shell.setSize(640, 480);
	    
	    shell.open();
	    while (!shell.isDisposed())
		{
			if (!display.readAndDispatch())
			{
				display.sleep();
			}
		}
		display.dispose();

	}
	
}
