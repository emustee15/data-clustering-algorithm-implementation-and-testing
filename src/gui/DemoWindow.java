/*
 * This class is for GUI testing purposes only. 
 * Will be removed from final version.
 */

package gui;

import org.eclipse.swt.*;
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
import org.eclipse.swt.widgets.TabFolder;

import selectionListeners.ExitBehavior;
import selectionListeners.OpenFileBehavior;
import selectionListeners.ShowOrHideExpandBar;
import org.eclipse.swt.widgets.Menu;

@Deprecated
public class DemoWindow
{

	static Label label;
	static OpenFileBehavior openWindow;
	static Display display;
	static ExpandBar exbar;
	static ErrorDialog errorDialog;
	
	public static void main(String[] args)
	{
		display = new Display();
		Shell shell = new Shell(display);
		shell.setText("This is a test!");
		GridLayout shellLayout = new GridLayout(2, false);
		shell.setLayout(shellLayout);
		
		Button button_1 = new Button(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		
		final TabFolder tabFolder = new TabFolder(shell, SWT.BORDER);
		
		errorDialog = new ErrorDialog(shell, shell.getStyle());
		exbar = new ExpandBar(shell, SWT.V_SCROLL);;
		exbar.setLayoutData(new GridData(SWT.TOP,SWT.LEFT,false,false,1,1));
		
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
		
		createMenuBar(shell);
		shell.setSize(640, 480);
		
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		
		MenuItem mntmHelp = new MenuItem(menu, SWT.NONE);
		mntmHelp.setText("Help");
		shell.open();
		shell.layout();
	
		errorDialog.open("This is a test.");
		
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
		bar.addSelectionListener(new ShowOrHideExpandBar(exbar, shell,bar.getItem("Toggle View")), "Toggle View");
		
		bar.addAccelerator("Zoom in", SWT.CTRL + '+');
		bar.addAccelerator("Zoom out", SWT.CTRL + '-');
		bar.addAccelerator("Select All", SWT.CTRL + 'A');
	}
}
