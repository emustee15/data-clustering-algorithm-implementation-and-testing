package GUI;
import org.eclipse.swt.*;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
public class DemoWindow
{
	public static void main(String[] args)
	{
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("This is a test!");
		
		
		Label label = new Label(shell,SWT.BORDER);
		label.setText("HI!");
		
		Text text = new Text(shell,SWT.BORDER);
		text.setLayoutData(new RowData(100, SWT.DEFAULT));
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
}
