package GUI;
import org.eclipse.swt.*;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import selectionListeners.ExitBehavior;
import selectionListeners.OpenFileBehavior;
import selectionListeners.ZoomInBehavior;

public class DemoWindow
{

	static Label label;
	static OpenFileBehavior openWindow;
	static Text textView;
	static Display display;
	
	public static void main(String[] args)
	{
		display = new Display();
		Shell shell = new Shell(display);
		shell.setText("This is a test!");
		ExpandBar bar = new ExpandBar(shell, SWT.V_SCROLL);
	
		Composite composite = new Composite(bar,SWT.None);
		composite.setBackground(new Color(Display.getCurrent(),245,246,247));
	    GridLayout compositeLayout = new GridLayout();
	    compositeLayout.marginLeft = compositeLayout.marginTop = compositeLayout.marginRight = compositeLayout.marginBottom = 10;
	    compositeLayout.verticalSpacing = 10;
	    composite.setLayout(compositeLayout);
	    
		GridLayout shellLayout = new GridLayout(4, false);
		shell.setLayout(shellLayout);
		GridData compositeLayoutData = new GridData(GridData.VERTICAL_ALIGN_FILL);
		compositeLayoutData.grabExcessVerticalSpace = true;
		bar.setLayoutData(compositeLayoutData);
		bar.setBackground(new Color(Display.getCurrent(), 240,240,240));
		Button button = new Button(composite, SWT.PUSH);
		button.setText("I am a button!");
		ExpandItem expandItem = new ExpandItem(bar, SWT.None);
		expandItem.setText("This can be expanded");
		expandItem.setHeight(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		expandItem.setControl(composite);
		expandItem.setExpanded(true);

		textView = new Text(shell, SWT.MULTI | SWT.BORDER | SWT.WRAP
				| SWT.V_SCROLL);
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_FILL);
		data.horizontalSpan = 3;
		data.grabExcessVerticalSpace = true;
		data.grabExcessHorizontalSpace = true;
		textView.setLayoutData(data);
		textView.pack();
		createMenuBar(shell);
		shell.pack();
		shell.setSize(640, 480);
		shell.open();
		
		GridData textGridData = (GridData)(textView.getLayoutData());
		textGridData.horizontalSpan = 4;
		
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
		bar.addSeperator("View");
		bar.addCheckBoxItem("View", "Toggle View", true);

		bar.addMenuItem("Help", "View Help");
		bar.addMenuItem("Help", "About");

		bar.addSelectionListener(new OpenFileBehavior(shell), "Open");
		bar.addSelectionListener(new ExitBehavior(), "Exit");
		bar.addSelectionListener(new ZoomInBehavior(textView, display), "Zoom in");

		MenuItem openItem = bar.getItem("Open");
	}
}
