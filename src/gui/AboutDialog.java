package gui;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Button;

import selectionListeners.CloseDialogBehavior;

public class AboutDialog extends Dialog
{

	protected Shell shlAboutRankedData;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public AboutDialog(Shell parent)
	{
		super(parent, SWT.DIALOG_TRIM);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public void open()
	{
		createContents();
		shlAboutRankedData.open();
		shlAboutRankedData.layout();
		Display display = getParent().getDisplay();
		while (!shlAboutRankedData.isDisposed())
		{
			if (!display.readAndDispatch())
			{
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents()
	{
		shlAboutRankedData = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.PRIMARY_MODAL);
		shlAboutRankedData.setSize(502, 393);
		shlAboutRankedData.setText("About");
		
		Label label = new Label(shlAboutRankedData, SWT.NONE);
		label.setImage(SWTResourceManager.getImage(AboutDialog.class, "/images/icon.png"));
		label.setBounds(-151, -169, 493, 556);
		
		Label lblRankedDataClusterer = new Label(shlAboutRankedData, SWT.NONE);
		lblRankedDataClusterer.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		lblRankedDataClusterer.setBounds(352, 10, 134, 192);
		lblRankedDataClusterer.setText("Ranked Data Clusterer \r\n\r\nVersion 1.0\r\n\r\nEric Mustee\r\nDan Skrodzki\r\nBrenan Mikolajczyk\r\n\r\nDecember 2014");
		
		Button btnGotIt = new Button(shlAboutRankedData, SWT.NONE);
		btnGotIt.setBounds(411, 329, 75, 25);
		btnGotIt.setText("Got it!");
		btnGotIt.addSelectionListener(new CloseDialogBehavior(shlAboutRankedData));

	}
}
