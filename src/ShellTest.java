import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.wb.swt.SWTResourceManager;


public class ShellTest extends Shell
{
	private Text text;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[])
	{
		try
		{
			Display display = Display.getDefault();
			ShellTest shell = new ShellTest(display);
			shell.open();
			shell.layout();
			while (!shell.isDisposed())
			{
				if (!display.readAndDispatch())
				{
					display.sleep();
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Create the shell.
	 * @param display
	 */
	public ShellTest(Display display)
	{
		super(display, SWT.SHELL_TRIM);
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		setLayout(new GridLayout(2, false));
		
		ExpandBar expandBar = new ExpandBar(this, SWT.NONE);
		expandBar.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		GridData gd_expandBar = new GridData(SWT.LEFT, SWT.TOP, false, true, 1, 1);
		gd_expandBar.widthHint = 267;
		expandBar.setLayoutData(gd_expandBar);
		
//		ExpandItem xpndtmExpandItem = new ExpandItem(expandBar, SWT.NONE);
//		xpndtmExpandItem.setExpanded(true);
//		xpndtmExpandItem.setText("Expand Item 1");
//		
//		Composite composite = new Composite(expandBar, SWT.NONE);
//		xpndtmExpandItem.setControl(composite);
//		xpndtmExpandItem.setHeight(xpndtmExpandItem.getControl().computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
//		
//		Button btnNewButton = new Button(composite, SWT.NONE);
//		btnNewButton.setBounds(91, 10, 75, 44);
//		btnNewButton.setText("New Button");
//		
//		Spinner spinner = new Spinner(composite, SWT.BORDER);
//		spinner.setBounds(23, 20, 47, 22);
//		
//		ExpandItem xpndtmExpandItem_1 = new ExpandItem(expandBar, SWT.NONE);
//		xpndtmExpandItem_1.setText("Expand Item 2");
		
		text = new Text(this, SWT.BORDER | SWT.V_SCROLL);
		text.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		Menu menu = new Menu(this, SWT.BAR);
		setMenuBar(menu);
		
		MenuItem mntmFile = new MenuItem(menu, SWT.CASCADE);
		mntmFile.setText("File");
		
		Menu menu_1 = new Menu(mntmFile);
		mntmFile.setMenu(menu_1);
		
		MenuItem mntmTest = new MenuItem(menu_1, SWT.NONE);
		mntmTest.setText("test");
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents()
	{
		setText("SWT Application");
		setSize(589, 557);

	}

	@Override
	protected void checkSubclass()
	{
		// Disable the check that prevents subclassing of SWT components
	}
}
