package GUI;
import org.eclipse.swt.*;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

//This can just be considered another test GUI. We will end up using a standard menu
//bar and the program will start up on the main screen.
public class StartScreen 
{
	
	static Label label;
	
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
		
		label = new Label(shell,SWT.BORDER);
		label.setText("Please load in a file.");
		label.setBounds(0, 0, 20, 20);
		
		Button loadButton = new Button(shell, SWT.PUSH);
		loadButton.setText("Load File");
		loadButton.setLayoutData(new RowData(100, 50));
		Button helpButton = new Button(shell, SWT.PUSH);
		helpButton.setText("Help");
		helpButton.setLayoutData(new RowData(100, 50));
		
		loadButton.addSelectionListener(new SelectionListener()
		{
			
			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				FileDialog dialog = new FileDialog(shell, SWT.OPEN);
				dialog.setText("Open");
				dialog.setFilterPath("C:/");
				String[] filterExt = {"*.txt"};
				dialog.setFilterExtensions(filterExt);
				String selectedFile = dialog.open();
				if (selectedFile!=null)
				{
					label.setText(selectedFile);
					label.pack();
				}
				else
				{
					label.setText("No file selected");
					label.pack();
				}
				
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0)
			{
				// TODO Auto-generated method stub
				
			}
		});
		
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
