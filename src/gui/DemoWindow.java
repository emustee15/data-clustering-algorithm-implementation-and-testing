package gui;
import java.awt.Event;

import org.eclipse.swt.*;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tracker;

import selectionListeners.CopyBehavior;
import selectionListeners.CutBehavior;
import selectionListeners.ExitBehavior;
import selectionListeners.OpenFileBehavior;
import selectionListeners.PasteBehavior;
import selectionListeners.SelectAllBehavior;
import selectionListeners.ShowOrHideExpandBar;
import selectionListeners.ZoomInBehavior;
import selectionListeners.ZoomOutBehavior;

public class DemoWindow
{

	static Label label;
	static OpenFileBehavior openWindow;
	static Text text;
	static Display display;
	static ExpandBar exbar;
	
	public static void main(String[] args)
	{
		display = new Display();
		Shell shell = new Shell(display);
		shell.setText("This is a test!");
		GridLayout shellLayout = new GridLayout(2, false);
		shell.setLayout(shellLayout);
		
		final TabFolder tabFolder = new TabFolder(shell, SWT.BORDER);
		
		
		exbar = new ExpandBar(shell, SWT.V_SCROLL);
		GridData barGridData = new GridData(SWT.TOP,SWT.LEFT,false,false,1,1);;
		exbar.setLayoutData(barGridData);
		
		Composite composite = new Composite(exbar,SWT.None);
		composite.setBackground(new Color(Display.getCurrent(),245,246,247));
	    GridLayout compositeLayout = new GridLayout();
	    compositeLayout.marginLeft = compositeLayout.marginTop = compositeLayout.marginRight = compositeLayout.marginBottom = 10;
	    compositeLayout.verticalSpacing = 10;
	    composite.setLayout(compositeLayout);
	    
		
		exbar.setBackground(new Color(Display.getCurrent(), 240,240,240));
		
	
		Button button = new Button(composite, SWT.PUSH);
		button.setText("I am a button!");
		ExpandItem expandItem = new ExpandItem(exbar, SWT.None);
		expandItem.setText("This can be expanded");
		expandItem.setHeight(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		expandItem.setControl(composite);
		expandItem.setExpanded(true);

		text = new Text(shell, SWT.MULTI | SWT.BORDER | SWT.WRAP
				| SWT.V_SCROLL);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		
		text.setLayoutData(data);
		
		createMenuBar(shell);
		shell.setSize(640, 480);
		shell.open();
		shell.layout();
	
		
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
		bar.addSeperator("Edit");
		bar.addMenuItem("Edit", "Select All");

		bar.addMenuItem("View", "Zoom in");
		bar.addMenuItem("View", "Zoom out");
		bar.addSeperator("View");
		bar.addCheckBoxItem("View", "Toggle View", true);

		bar.addMenuItem("Help", "View Help");
		bar.addMenuItem("Help", "About");

		//bar.addSelectionListener(new OpenFileBehavior(shell), "Open");
		bar.addSelectionListener(new ExitBehavior(), "Exit");
		bar.addSelectionListener(new ZoomInBehavior(text, display, bar.getItem("Zoom out"),bar.getItem("Zoom in")), "Zoom in");
		bar.addSelectionListener(new ZoomOutBehavior(text, display, bar.getItem("Zoom out"),bar.getItem("Zoom in")), "Zoom out");
		bar.addSelectionListener(new ShowOrHideExpandBar(exbar, shell,bar.getItem("Toggle View")), "Toggle View");
		bar.addSelectionListener(new SelectAllBehavior(text), "Select All");
		bar.addSelectionListener(new CutBehavior(text), "Cut");
		bar.addSelectionListener(new CopyBehavior(text), "Copy");
		bar.addSelectionListener(new PasteBehavior(text), "Paste");
		
		bar.addAccelerator("Zoom in", SWT.CTRL + '+');
		bar.addAccelerator("Zoom out", SWT.CTRL + '-');
		bar.addAccelerator("Select All", SWT.CTRL + 'A');
	}
}
