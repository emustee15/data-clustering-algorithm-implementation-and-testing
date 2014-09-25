package GUI;

import org.eclipse.swt.*;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import selectionListeners.ExitBehavior;
import selectionListeners.OpenFileBehavior;

public class DemoWindow
{

	static Label label;
	static OpenFileBehavior openWindow;

	public static void main(String[] args)
	{
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("This is a test!");

		createMenuBar(shell);

		label = new Label(shell, SWT.BORDER);
		label.setText("HI!");
		label.setBounds(10, 10, 20, 20);

		Text text = new Text(shell, SWT.MULTI | SWT.BORDER | SWT.WRAP
				| SWT.V_SCROLL);
		text.setBounds(200, 50, 200, 200);
		Button ok = new Button(shell, SWT.PUSH);
		ok.setBounds(100, 50, 100, 100);
		ok.setText("OK");
		ok.addSelectionListener(new SelectionListener()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				System.out.println("OK!");
				new DemoWindow();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0)
			{
				// TODO Auto-generated method stub

			}
		});
		// shell.setLayout(new RowLayout());
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

	public static void createMenuBar(Shell shell)
	{
		MenuBar bar = new MenuBar(shell);

		bar.addTopLevelMenu("File");
		bar.addTopLevelMenu("Edit");
		bar.addTopLevelMenu("View");
		bar.addTopLevelMenu("Help");

		bar.addMenuItem("File", "New");
		bar.addSeperator("File");
		bar.addMenuItem("File", "Open");
		bar.addMenuItem("File", "Save");
		bar.addMenuItem("File", "Exit");

		bar.addMenuItem("Edit", "Cut");
		bar.addMenuItem("Edit", "Copy");
		bar.addMenuItem("Edit", "Paste");

		bar.addMenuItem("View", "Zoom in");
		bar.addMenuItem("View", "Zoom out");
		bar.addCheckBoxItem("View", "Toggle View", true);

		bar.addMenuItem("Help", "View Help");
		bar.addMenuItem("Help", "About");

		bar.addSelectionListener(new OpenFileBehavior(shell), "Open");
		bar.addSelectionListener(new ExitBehavior(), "Exit");

		MenuItem openItem = bar.getItem("Open");
	}
}
