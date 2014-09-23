package GUI;
import org.eclipse.swt.*;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
public class DemoWindow
{
	
	static Label label;
	public static void main(String[] args)
	{
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("This is a test!");
		
		createMenuBar(shell);

		label = new Label(shell,SWT.BORDER);
		label.setText("HI!");
		label.setBounds(10, 10, 20, 20);
		
		Text text = new Text(shell,SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		text.setBounds(200, 50, 200, 200);
		Button ok = new Button(shell, SWT.PUSH);
		ok.setBounds(100, 50, 100, 100);
		ok.setText("OK");
		ok.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				System.out.println("OK!");
				new DemoWindow();
			}
		});
		//shell.setLayout(new RowLayout());
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
	
	public static void createMenuBar(final Shell shell)
	{
		Menu menuBar = new Menu(shell,SWT.BAR);
		MenuItem cascadeFileMenu = new MenuItem(menuBar, SWT.CASCADE);
		cascadeFileMenu.setText("&File");
		Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
		cascadeFileMenu.setMenu(fileMenu);
		
		
		MenuItem openFile = new MenuItem(fileMenu,SWT.PUSH);
		openFile.setText("Open");
		
		openFile.addSelectionListener(new SelectionListener()
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
		
		MenuItem exitItem = new MenuItem(fileMenu,SWT.PUSH);
		exitItem.setText("Exit");
		
		final MenuItem checkItem = new MenuItem(fileMenu, SWT.CHECK);
		checkItem.setText("Check Me ;)");
		checkItem.addSelectionListener(new SelectionListener()
		{
			
			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				if (checkItem.getSelection())
				{
					checkItem.setText("Uncheck Me ;)");
				}
				else
				{
					checkItem.setText("Check Me ;)");
				}
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0)
			{
				// TODO Auto-generated method stub
				
			}
		});
		
		shell.setMenuBar(menuBar);
	}
}
